package state;

import model.Booking;
import model.Consumer;
import model.EventPerformance;

import java.util.List;

public interface IBookingState {

    /**
     *
     * @param booker
     * @param performance
     * @param numTickets
     * @param amountPaid
     * @return
     */
    Booking createBooking(Consumer booker, EventPerformance performance, int numTickets, double amountPaid);

    /**
     *
     * @param bookingNumber
     * @return
     */
    Booking findBookingByNumber(long bookingNumber);

    /**
     *
     * @param eventNumber
     * @return
     */
    List<Booking> findBookingsByEventNumber(long eventNumber);

}