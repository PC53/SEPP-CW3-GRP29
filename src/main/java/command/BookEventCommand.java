package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;
import java.util.Map;

public class BookEventCommand extends Object implements ICommand{

    enum LogStatus{
        BOOK_EVENT_SUCCESS,
        BOOK_EVENT_USER_NOT_CONSUMER,
                BOOK_EVENT_NOT_A_TICKETED_EVENT,
        BOOK_EVENT_EVENT_NOT_ACTIVE,
                BOOK_EVENT_ALREADY_OVER,
        BOOK_EVENT_INVALID_NUM_TICKETS,
                BOOK_EVENT_NOT_ENOUGH_TICKETS_LEFT,
        BOOK_EVENT_PAYMENT_FAILED,
                BOOK_EVENT_EVENT_NOT_FOUND,
        BOOK_EVENT_PERFORMANCE_NOT_FOUND
    }

    private void logResult(BookEventCommand.LogStatus status){
        Logger.getInstance().logAction("command.BookEventCommand",status);
    }

    private final long eventNumber;
    private final long performanceNumber;
    private final int numTicketsRequested;
    private boolean result = false;
    private Booking booking;

    public BookEventCommand(long eventNumber,
                            long performanceNumber,
                            int numTicketsRequested){
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
        this.numTicketsRequested = numTicketsRequested;
    }

    @Override
    public void execute(Context context) {
        User currUser = context.getUserState().getCurrentUser();
        if(currUser instanceof Consumer){
            Event event = context.getEventState().findEventByNumber(eventNumber);
            if(event == null){
                logResult(LogStatus.BOOK_EVENT_EVENT_NOT_FOUND);
                return;
            }
            if(event.getStatus() != EventStatus.ACTIVE){
                logResult(LogStatus.BOOK_EVENT_EVENT_NOT_ACTIVE);
                return;
            }
            if(event instanceof TicketedEvent){
                if(numTicketsRequested >= 1){
                    EventPerformance performance = event.getPerformanceByNumber(performanceNumber);
                    if(performance!=null){
                        LocalDateTime now = LocalDateTime.now();
                        if(now.isBefore(performance.getEndDateTime())){
                            int ticketsLeft = event.getOrganiser().getProviderSystem().getNumTicketsLeft(eventNumber,performanceNumber);
                            if(ticketsLeft>=numTicketsRequested){
                                 double amountToPay = ((TicketedEvent) event).getDiscountedTicketPrice() * numTicketsRequested;
                                 String buyerEmail = currUser.getPaymentAccountEmail();
                                 String sellerEmail = event.getOrganiser().getPaymentAccountEmail();

                                booking = context
                                        .getBookingState()
                                        .createBooking((Consumer) currUser,performance,numTicketsRequested,amountToPay);

                                if(context.getPaymentSystem().processPayment(buyerEmail,sellerEmail,amountToPay)){
                                    result = true;

                                     // record in Entertainment provider system
                                    event.getOrganiser().getProviderSystem().recordNewBooking(
                                            eventNumber,
                                            performanceNumber,
                                            booking.getBookingNumber(),
                                            ((Consumer) currUser).getName(),
                                            currUser.getEmail(),
                                            numTicketsRequested);
                                    logResult(LogStatus.BOOK_EVENT_SUCCESS);
                                }else {
                                    result = false;
                                    booking.cancelPaymentFailed();
                                    logResult(LogStatus.BOOK_EVENT_PAYMENT_FAILED);
                                }
                            }else logResult(LogStatus.BOOK_EVENT_NOT_ENOUGH_TICKETS_LEFT);
                        }else logResult(LogStatus.BOOK_EVENT_ALREADY_OVER);
                    }else logResult(LogStatus.BOOK_EVENT_PERFORMANCE_NOT_FOUND);
                }else logResult(LogStatus.BOOK_EVENT_INVALID_NUM_TICKETS);
            }else logResult(LogStatus.BOOK_EVENT_NOT_A_TICKETED_EVENT);
        }else logResult(LogStatus.BOOK_EVENT_USER_NOT_CONSUMER);

    }

    @Override
    public Long getResult() {
        if(result){
            return booking.getBookingNumber();
        }
        else{
            return null;
        }
    }
}
