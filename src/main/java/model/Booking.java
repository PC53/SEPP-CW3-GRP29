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


    public Booking(long bookingNumber,
                   Consumer booker,
                   EventPerformance performance,
                   int numTickets,
                   double amountPaid,
                   LocalDateTime bookingDateTime) {
        this.bookingNumber = bookingNumber;
        this.booker = booker;
        this.performance = performance;
        this.numTickets = numTickets;
        this.amountPaid = amountPaid;
        this.bookingDateTme = bookingDateTime;
        this.status = BookingStatus.Active;
    }

    public long getBookingNumber() {
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

    public double getAmountPaid() {
        return amountPaid;
    }

    public void cancelByConsumer() {
        status = BookingStatus.CancelledByConsumer;
    }

    public void cancelPaymentFailed() {
        status = BookingStatus.PaymentFailed;
    }

    public void cancelByProvider() {
        status = BookingStatus.CancelledByProvider;
    }


    @Override
    public String toString() {
        return "Booking{" +
                "bookingNumber=" + bookingNumber +
                ", booker=" + booker +
                ", performance=" + performance +
                ", numTickets=" + numTickets +
                ", amountPaid=" + amountPaid +
                ", bookingDateTme=" + bookingDateTme +
                ", status=" + status +
                '}';
    }
}
