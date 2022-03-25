package state;

import model.Booking;
import model.Consumer;
import model.EventPerformance;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Objects;

public class BookingState extends Object implements IBookingState{

    private long nextBookingNumber;
    private List<Booking> Bookings;

    public BookingState() {
        this.nextBookingNumber = 1;
        this.Bookings = new ArrayList<>();
    }

    public BookingState(IBookingState other) {
        this.nextBookingNumber = ((BookingState)other).nextBookingNumber;
        this.Bookings = new ArrayList<>(((BookingState)other).Bookings);
    }

    @Override
    public Booking createBooking(Consumer booker, EventPerformance performance, int numTickets, double amountPaid) {
        Booking instance = new Booking(nextBookingNumber, booker, performance, numTickets, amountPaid, LocalDateTime.now());
        Bookings.add(instance);
        nextBookingNumber++;
        return instance;
    }

    @Override
    public Booking findBookingByNumber(long bookingNumber) {
        for (int i = 0; i < Bookings.size(); i++) {
            if (Objects.equals(Bookings.get(i).getBookingNumber(), bookingNumber)) {
                return Bookings.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Booking> findBookingsByEventNumber(long eventNumber) {
        List<Booking> bookingsForEvent = new ArrayList<>();
        for (int i = 0; i < Bookings.size(); i++) {
            if (Objects.equals(Bookings.get(i).getEventPerformance().getEvent().getEventNumber(), eventNumber)) {
                bookingsForEvent.add(Bookings.get(i));
            }
        }
        // Carefully with NonTicketedEvent
        return bookingsForEvent;
    }
}
