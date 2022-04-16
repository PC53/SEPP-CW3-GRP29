import logging.Logger;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import state.BookingState;
import state.EventState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBookingState {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static Consumer registerConsumer() {
        Consumer consumer = new Consumer(
                "Julius Temitope",
                "juliustemitope17@gmail.com",
                "07438462934",
                "abcdef",
                "juliustemitope7@gmail.com"
        );
        return consumer;
    }

    private static Event createNewEvent1(EventState eventState) {
        EntertainmentProvider newEP = new EntertainmentProvider(
                "Jackson Incorporated",
                "EH8 123",
                "jackson123@gmail.com",
                "Jackson Mike",
                "jacksonmike@gmail.com",
                "abcedefg",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        Event instance = eventState.createTicketedEvent(
                newEP,
                "Test One",
                EventType.Movie,
                1.0,
                100
        );
        return instance;
    }

    private static Event createNewEvent2(EventState eventState) {
        EntertainmentProvider newEP = new EntertainmentProvider(
                "Jackson Incorporated",
                "EH8 123",
                "jackson123@gmail.com",
                "Jackson Mike",
                "jacksonmike@gmail.com",
                "abcedefg",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        Event instance = eventState.createTicketedEvent(
                newEP,
                "Test Two",
                EventType.Movie,
                1.0,
                100
        );
        return instance;
    }

    private static EventPerformance createNewEventPerformance(Event event, int performanceNumber) {
        EventPerformance instance = new EventPerformance(
                performanceNumber,
                event,
                "Test Address",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(6),
                new ArrayList<>(),
                false,
                false,
                false,
                500,
                5000
        );
        return instance;
    }

    @Test
    void createOneBookingTests() {
        BookingState bookingState = new BookingState();
        EventState eventState = new EventState();

        Consumer consumer1 = registerConsumer();
        Event event = createNewEvent1(eventState);
        EventPerformance eventPerformance = createNewEventPerformance(event, 1);
        Booking instance = bookingState.createBooking(consumer1, eventPerformance, 1, 1.0);

        assertEquals(consumer1, instance.getBooker());
        assertEquals(eventPerformance, instance.getEventPerformance());
        assertEquals(1.0, instance.getAmountPaid());
    }

    @Test
    void findBookingWithBookingNumberTests() {
        BookingState bookingState = new BookingState();
        EventState eventState = new EventState();

        Consumer consumer1 = registerConsumer();
        Event event = createNewEvent1(eventState);
        EventPerformance eventPerformance = createNewEventPerformance(event, 1);
        Booking instance1 = bookingState.createBooking(consumer1, eventPerformance, 1, 1.0);
        Booking instance2 = bookingState.createBooking(consumer1, eventPerformance, 2, 2.0);
        Booking instance3 = bookingState.createBooking(consumer1, eventPerformance, 3, 3.0);

        Booking instanceFound = bookingState.findBookingByNumber(instance2.getBookingNumber());
        assertEquals(instance2, instanceFound);
    }

    @Test
    void findBookingWithEventNumberTests() {
        BookingState bookingState = new BookingState();
        EventState eventState = new EventState();

        Consumer consumer1 = registerConsumer();
        Event event1 = createNewEvent1(eventState);
        Event event2 = createNewEvent2(eventState);
        EventPerformance eventPerformance1 = createNewEventPerformance(event1, 1);
        EventPerformance eventPerformance2 = createNewEventPerformance(event2, 2);
        Booking instance1 = bookingState.createBooking(consumer1, eventPerformance1, 1, 1.0);
        Booking instance2 = bookingState.createBooking(consumer1, eventPerformance1, 2, 2.0);
        Booking instance3 = bookingState.createBooking(consumer1, eventPerformance2, 1, 1.0);

        List<Booking> bookings = bookingState.findBookingsByEventNumber(event1.getEventNumber());
        assertEquals(2, bookings.size());
        assertEquals(instance1, bookings.get(0));
        assertEquals(instance2, bookings.get(1));
    }

    @Test
    void copyBookingsTests() {
        BookingState bookingState1 = new BookingState();
        EventState eventState = new EventState();

        Consumer consumer = registerConsumer();
        Event event = createNewEvent1(eventState);
        EventPerformance eventPerformance = createNewEventPerformance(event, 1);
        Booking instance1 = bookingState1.createBooking(consumer, eventPerformance, 1, 1.0);

        // Copy bookingState1 to bookingState2
        BookingState bookingState2 = new BookingState(bookingState1);

        // Test whether existed bookings are copied
        Booking instance2 = bookingState2.findBookingByNumber(1);

        assertEquals(instance1, instance2);
        assertEquals(consumer, instance2.getBooker());
        assertEquals(eventPerformance, instance2.getEventPerformance());
        assertEquals(1.0, instance2.getAmountPaid());

        // Test whether existed nextBookingNumber is copied
        Booking instance3 = bookingState2.createBooking(consumer, eventPerformance, 3, 3.0);

        assertEquals(instance1.getBookingNumber() + 1, instance3.getBookingNumber());
    }
}
