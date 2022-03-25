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

    public void cancelByConsumer() {

    }

    public void cancelPaymentFailed() {

    }

    public void cancelByProvider() {

    }

    @Override
    public String toString() {
        return null;
    }
}
