package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CancelEventCommand extends Object implements ICommand{

    enum LogStatus{
        CANCEL_EVENT_SUCCESS,
        CANCEL_EVENT_MESSAGE_MUST_NOT_BE_BLANK,
                CANCEL_EVENT_USER_NOT_ENTERTAINMENT_PROVIDER,
        CANCEL_EVENT_EVENT_NOT_FOUND,
                CANCEL_EVENT_NOT_ACTIVE,
        CANCEL_EVENT_USER_NOT_ORGANISER,
                CANCEL_EVENT_PERFORMANCE_ALREADY_STARTED,
        CANCEL_EVENT_REFUND_SPONSORSHIP_SUCCESS,
                CANCEL_EVENT_REFUND_SPONSORSHIP_FAILED,
        CANCEL_EVENT_REFUND_BOOKING_SUCCESS,
                CANCEL_EVENT_REFUND_BOOKING_ERROR
    }

    private void logResult(CancelEventCommand.LogStatus status){
        Logger.getInstance().logAction("command.CancelEventCommand",status);
    }

    private final String organiserMessage;
    private final long eventNumber;

    private boolean result = false;

    public CancelEventCommand(long eventNumber,
                              String organiserMessage){
        this.eventNumber = eventNumber;
        this.organiserMessage = organiserMessage;
    }
    
    @Override
    public void execute(Context context) {
        if(! organiserMessage.equals("")){
            User currUser = context.getUserState().getCurrentUser();
            if(currUser instanceof EntertainmentProvider){
                Event event = context.getEventState().findEventByNumber(eventNumber);
                if(event != null){
                    if(event.getStatus() == EventStatus.ACTIVE){
                        if(event.getOrganiser().getEmail().equals(currUser.getEmail())){

                            Collection<EventPerformance> performances = event.getPerformances();
                            LocalDateTime now = LocalDateTime.now();

                            boolean performanceResult = true;
                            for (EventPerformance performance : performances) {
                                if (now.isAfter(performance.getStartDateTime())
                                        && now.isBefore(performance.getEndDateTime())) {
                                    performanceResult = false;
                                    break;
                                }
                            }
                            if(performanceResult) {
                                if (event instanceof TicketedEvent) {
                                    if (((TicketedEvent) event).isSponsored()) {
                                        if (refundSponsorship(context, (TicketedEvent) event)){
                                            result = true;
                                            logResult(LogStatus.CANCEL_EVENT_REFUND_SPONSORSHIP_SUCCESS);
                                        }else {
                                            logResult(LogStatus.CANCEL_EVENT_REFUND_SPONSORSHIP_FAILED);
                                            result = false;
                                        }
                                    }
                                    if(refundBookings(context, (TicketedEvent) event)){
                                        result = true;
                                        logResult(LogStatus.CANCEL_EVENT_REFUND_BOOKING_SUCCESS);
                                    }else {
                                        logResult(LogStatus.CANCEL_EVENT_REFUND_BOOKING_ERROR);
                                        result = false;
                                    }


                                    if (result) {
                                        event.cancel();
                                        // record in Entertainment provider system
                                        event.getOrganiser().getProviderSystem().cancelEvent(eventNumber, organiserMessage);

                                        // cancel all the bookings
                                        for (Booking booking : context.getBookingState().findBookingsByEventNumber(eventNumber)) {
                                            booking.cancelByProvider();
                                        }

                                        logResult(LogStatus.CANCEL_EVENT_SUCCESS);

                                    }
                                }

                                else if (event instanceof NonTicketedEvent) {
                                    event.cancel();
                                    event.getOrganiser().getProviderSystem().cancelEvent(eventNumber, organiserMessage);
                                    result = true;
                                }
                            }else logResult(LogStatus.CANCEL_EVENT_PERFORMANCE_ALREADY_STARTED);
                        }else logResult(LogStatus.CANCEL_EVENT_USER_NOT_ORGANISER);
                    }else logResult(LogStatus.CANCEL_EVENT_NOT_ACTIVE);
                }else logResult(LogStatus.CANCEL_EVENT_EVENT_NOT_FOUND);
            }else logResult(LogStatus.CANCEL_EVENT_USER_NOT_ENTERTAINMENT_PROVIDER);
        }else logResult(LogStatus.CANCEL_EVENT_MESSAGE_MUST_NOT_BE_BLANK);
    }

    private boolean refundBookings(Context context,TicketedEvent event){
        long eventNumber = event.getEventNumber();

        List<Booking> bookings = context.getBookingState().findBookingsByEventNumber(eventNumber);

        boolean successfulRefunds = true;
        for(Booking booking : bookings){
            String bookerEmail = booking.getBooker().getPaymentAccountEmail();
            String epEmail = booking.getEventPerformance().getEvent().getOrganiser().getPaymentAccountEmail();
            double amtPaid = booking.getAmountPaid();
            booking.cancelByProvider();

            successfulRefunds = (context.getPaymentSystem().processRefund(bookerEmail,epEmail,amtPaid));
        }

        return successfulRefunds;
    }

    private boolean refundSponsorship(Context context, TicketedEvent event){

        int numTickets = event.getNumTickets();
        double discount = event.getOriginalTicketPrice() - event.getDiscountedTicketPrice() ;
        String govEmail = event.getSponsorAccountEmail();
        String orgEmail = event.getOrganiser().getPaymentAccountEmail();

        return (context.getPaymentSystem().processRefund(govEmail,orgEmail,discount*numTickets));
    }

    @Override
    public Object getResult() {
        return result;
    }
}
