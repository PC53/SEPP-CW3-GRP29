package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.time.Duration;

import java.time.LocalDateTime;
import java.util.Map;

public class CancelBookingCommand extends Object implements ICommand{
    enum LogStatus{CANCEL_BOOKING_SUCCESS,
        CANCEL_BOOKING_USER_NOT_CONSUMER,
                CANCEL_BOOKING_BOOKING_NOT_FOUND,
        CANCEL_BOOKING_USER_IS_NOT_BOOKER,
                CANCEL_BOOKING_BOOKING_NOT_ACTIVE,
        CANCEL_BOOKING_REFUND_FAILED,
                CANCEL_BOOKING_NO_CANCELLATIONS_WITHIN_24H
    }

    private void logResult(CancelBookingCommand.LogStatus status){
        Logger.getInstance().logAction("command.CancelBookingCommand",status);
    }

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
                            EntertainmentProvider ep = booking.getEventPerformance().getEvent().getOrganiser();
                            String sellerEmail = ep.getPaymentAccountEmail();
                            if(context.getPaymentSystem().processRefund(buyerEmail,sellerEmail,amountPaid)){
                                this.result = true;
                                booking.cancelByConsumer();

                                // record in Entertainment provider system
                                ep.getProviderSystem().cancelBooking(bookingNumber);
                                logResult(LogStatus.CANCEL_BOOKING_SUCCESS);

                            }else logResult(LogStatus.CANCEL_BOOKING_REFUND_FAILED);

                        }else logResult(LogStatus.CANCEL_BOOKING_NO_CANCELLATIONS_WITHIN_24H);
                    }else logResult(LogStatus.CANCEL_BOOKING_BOOKING_NOT_ACTIVE);
                }else logResult(LogStatus.CANCEL_BOOKING_USER_IS_NOT_BOOKER);
            }else logResult(LogStatus.CANCEL_BOOKING_BOOKING_NOT_FOUND);
        }else logResult(LogStatus.CANCEL_BOOKING_USER_NOT_CONSUMER);
    }

    @Override
    public Boolean getResult() {
        return result;
    }

}
