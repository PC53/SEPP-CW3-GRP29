package command;

import controller.Context;
import model.Booking;
import model.BookingStatus;
import model.Consumer;
import model.User;
import java.time.Duration;

import java.time.LocalDateTime;

public class CancelBookingCommand extends Object implements ICommand{
    private final long bookingNumber;
    private Boolean result = false;

    public CancelBookingCommand(long bookingNumber){
        this.bookingNumber = bookingNumber;
    }

    @Override
    public void execute(Context context) {
        // currently logged-in user is a Consumer
        User currUser = context.getUserState().getCurrentUser();
        if (currUser instanceof model.Consumer) {
            // the booking number corresponds to an existing Booking
            Booking booking = context.getBookingState().findBookingByNumber(bookingNumber);
            if(booking != null) {
                // the logged-in user is the booking owner
                Consumer booker = booking.getBooker();
                if (booker.getEmail().equals(currUser.getEmail())) {
                    // the booking is still active (i.e., not cancelled previously)
                    if (booking.getStatus() == BookingStatus.Active) {
                        // the booked performance start is at least 24h away from now
                        LocalDateTime startTime = booking.getEventPerformance().getStartDateTime();
                        Duration duration = Duration.between(LocalDateTime.now(), startTime);
                        if( duration.toHours() >= 24){
                            // the payment system refund succeeds
                            String buyerEmail = booker.getPaymentAccountEmail();
                            double amountPaid = booking.getAmountPaid();
                            String sellerEmail = booking.getEventPerformance().getEvent().getOrganiser().getPaymentAccountEmail();
                            if(context.getPaymentSystem().processRefund(buyerEmail,sellerEmail,amountPaid)){
                                this.result = true;
                                booking.cancelByConsumer(); // TODO : should this be called after getResult()??
                            }

                        }
                    }
                }
            }
        }
    }

    @Override
    public Boolean getResult() {
        return result;
    }
}
