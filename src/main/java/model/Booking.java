package model;

import java.time.LocalDateTime;

public class Booking extends Object {

    private final long bookingNumber;
    private final Consumer booker;
    private final EventPerformance performance;
    private final int numTickets;
    private final double amountPaid;
    private final LocalDateTime bookingDateTme;

    private BookingStatus status;

    private EventPerformance attribute;
    private BookingStatus attribute2;


    public Booking(long bookingNumber,
                   Consumer booker,
                   EventPerformance performance,
                   int numTickets,
                   double amountPaid,
                   LocalDateTime bookingDateTime)
    {
        this.bookingNumber = bookingNumber;
        this.booker = booker;
        this.performance = performance;
        this.numTickets = numTickets;
        this.amountPaid = amountPaid;
        this.bookingDateTme = bookingDateTime;
    }

    public long getBookingNumber(){
        return bookingNumber;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Consumer getBooker() {
        return booker;
    }

    public EventPerformance getEventPerformance() {
        return performance;
    }

    public double getAmountPaid(){return amountPaid;}

    public void cancelByConsumer() {
        // this may need to be revised to check if it valid to cancel the booking
        status = BookingStatus.CancelledByConsumer;
    }

    public void cancelPaymentFailed() {
        status = BookingStatus.PaymentFailed;
    }

    public void cancelByProvider() {
        status = BookingStatus.CancelledByProvider;
    }

    // Don't know what this is for.
    @Override
    public String toString() {
        return null;
    }
}
