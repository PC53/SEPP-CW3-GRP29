package command;

import controller.Context;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CancelEventCommand extends Object implements ICommand{

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

                            if(performanceResult && event instanceof TicketedEvent){
                                if(((TicketedEvent) event).isSponsored()){
                                    result =  refundSponsorship(context,(TicketedEvent)event);
                                }
                                result = refundBookings(context,(TicketedEvent)event);

                                if(result) {
                                    event.cancel();
                                    // record in Entertainment provider system
                                    event.getOrganiser().getProviderSystem().cancelEvent(eventNumber, organiserMessage);

                                    // cancel all the bookings
                                    for (Booking booking : context.getBookingState().findBookingsByEventNumber(eventNumber)){
                                        booking.cancelByProvider();
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
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
