package command;

import controller.Context;
import model.*;

import java.time.LocalDateTime;

public class BookEventCommand extends Object implements ICommand{
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
            if(event != null && event instanceof TicketedEvent){
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
                                }else {
                                    result = false;
                                    booking.cancelPaymentFailed();
                                }
                            }
                        }
                    }
                }
            }
        }

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
