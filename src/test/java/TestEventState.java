import logging.Logger;
import model.*;
import org.junit.jupiter.api.*;
import state.EventState;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TestEventState {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static EntertainmentProvider newEntertainmentProvider() {
        ArrayList<String> repNames = new ArrayList<>();
        ArrayList<String> repEmails = new ArrayList<>();
        EntertainmentProvider entertainmentProvider = new EntertainmentProvider(
                "University of Edinburgh",
                "Edinburgh",
                "uoe@ed.ac.uk",
                "Ning Guang",
                "ningguang@ed.ac.uk",
                "i am very secure",
                repNames,
                repEmails
        );
        return entertainmentProvider;
    }

    @Test
    @DisplayName("Creating One Valid Non Ticketed Event")
    void creatingValidNonTicketedEvent() {
        EventState eventState= new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();

        NonTicketedEvent nonTicketedEvent = eventState.createNonTicketedEvent(
                entertainmentProvider,
                "Charity Run",
                EventType.Sports
        );

        assertEquals("Charity Run", nonTicketedEvent.getTitle());
    }

    @Test
    @DisplayName("Creating One Invalid Non Ticketed Event")
    void creatingInvalidNonTicketedEvent() {
        EventState eventState = new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();
        NonTicketedEvent event = eventState.createNonTicketedEvent(null, null, null);

        assertEquals(1, eventState.getAllEvents().size());
    }

    @Test
    @DisplayName("Creating One Valid Ticketed Event")
    void createValidTicketedEvent() {
        EventState eventState = new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();
        TicketedEvent event = eventState.createTicketedEvent(
                entertainmentProvider,
                "Movie Night",
                EventType.Movie,
                13.00,
                100
        );
        assertEquals("Movie Night", event.getTitle());
        assertEquals(event, eventState.getAllEvents().get(0));
    }

    @Test
    @DisplayName("Creating One Valid Non Ticketed Event and One Event Performance")
    void createOneNonTicketedEventWithOnePerformance() {
        EventState eventState = new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();
        NonTicketedEvent event = eventState.createNonTicketedEvent(
                entertainmentProvider,
                "Dance competition",
                EventType.Dance
        );

        ArrayList<String> performers = new ArrayList<>();
        EventPerformance performance = eventState.createEventPerformance(
                event,
                "Bristol Square",
                LocalDateTime.now().plusWeeks(2),
                LocalDateTime.now().plusWeeks(2).plusHours(5),
                performers,
                false,
                false,
                true,
                200,
                200
        );
        assertEquals(performance, eventState.findEventByNumber(1).getPerformanceByNumber(1));
    }

    @Test
    @DisplayName("Creating One Ticketed Event and Multiple Performances")
    void createOneTicketedEventWithMultiplePerformances() {
        EventState eventState = new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();
        TicketedEvent event = eventState.createTicketedEvent(
                entertainmentProvider,
                "Movie Marathon",
                EventType.Movie,
                24.80,
                250
        );
        ArrayList<String> workers = new ArrayList<>();
        EventPerformance performance1 = eventState.createEventPerformance(
                event,
                "Movie Theatre",
                LocalDateTime.now().plusWeeks(2),
                LocalDateTime.now().plusWeeks(2).plusHours(2),
                workers,
                true,
                true,
                false,
                300,
                350
        );
        EventPerformance performance2 = eventState.createEventPerformance(
                event,
                "Movie Theatre",
                LocalDateTime.now().plusWeeks(2).plusHours(3),
                LocalDateTime.now().plusWeeks(2).plusHours(5),
                workers,
                true,
                true,
                false,
                300,
                350
        );
        EventPerformance performance3 = eventState.createEventPerformance(
                event,
                "Movie Theatre",
                LocalDateTime.now().plusWeeks(2).plusHours(6),
                LocalDateTime.now().plusWeeks(2).plusHours(8),
                workers,
                true,
                true,
                false,
                300,
                350
        );
        assertEquals(3, eventState.findEventByNumber(1).getPerformances().size());
    }

    @Test
    @DisplayName("Creating Multiple Events With One Performance Each")
    void creatingMultipleEventsWithOnePerformance() {
        EventState eventState = new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();

        TicketedEvent event1 = eventState.createTicketedEvent(
                entertainmentProvider,
                "Artist concert",
                EventType.Music,
                20.00,
                200
        );
        NonTicketedEvent event2 = eventState.createNonTicketedEvent(
                entertainmentProvider,
                "Busking event",
                EventType.Music
        );
        TicketedEvent event3 = eventState.createTicketedEvent(
                entertainmentProvider,
                "Orchestral Concert",
                EventType.Music,
                50.00,
                400
        );

        assertEquals(3, eventState.getAllEvents().size());
    }

    @Test
    @DisplayName("Creating Multiple Events With Multiple Event Performances")
    void createMultipleEventsWithMultiplePerformances() {
        EventState eventState = new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();

        TicketedEvent event1 = eventState.createTicketedEvent(
                entertainmentProvider,
                "Artist concert",
                EventType.Music,
                20.00,
                200
        );
        NonTicketedEvent event2 = eventState.createNonTicketedEvent(
                entertainmentProvider,
                "Busking event",
                EventType.Music
        );
        TicketedEvent event3 = eventState.createTicketedEvent(
                entertainmentProvider,
                "Orchestral Concert",
                EventType.Music,
                50.00,
                400
        );

        ArrayList<String> performers =  new ArrayList<>();
        EventPerformance event1Performance1 = eventState.createEventPerformance(
                event1,
                "Reid Music Hall",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(2),
                performers,
                true,
                true,
                false,
                200,
                250
        );
        EventPerformance event1Performance2 = eventState.createEventPerformance(
                event1,
                "Reid Music Hall",
                LocalDateTime.now().plusMonths(1).plusHours(2),
                LocalDateTime.now().plusMonths(1).plusHours(4),
                performers,
                true,
                true,
                false,
                200,
                250
        );
        EventPerformance event2Performance1 = eventState.createEventPerformance(
                event2,
                "Bristol Square",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(3),
                performers,
                false,
                false,
                true,
                400,
                400
        );
        EventPerformance event2Performance2 = eventState.createEventPerformance(
                event2,
                "Bristol Square",
                LocalDateTime.now().plusMonths(1).plusHours(1),
                LocalDateTime.now().plusMonths(1).plusHours(4),
                performers,
                false,
                false,
                true,
                400,
                400
        );
        EventPerformance event2Performance3 = eventState.createEventPerformance(
                event2,
                "Bristol Square",
                LocalDateTime.now().plusMonths(1).plusHours(3),
                LocalDateTime.now().plusMonths(1).plusHours(5),
                performers,
                false,
                false,
                true,
                400,
                400
        );
        EventPerformance event3Performance = eventState.createEventPerformance(
                event3,
                "SummerHall",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(4),
                performers,
                true,
                true,
                false,
                400,
                450
        );

        int totalPerformances = event1.getPerformances().size() +
                event2.getPerformances().size()+ event3.getPerformances().size();
        assertEquals(6, totalPerformances);
    }

    @Test
    @DisplayName("Cancel a created event")
    void cancellingCreatedEvent() {
        EventState eventState = new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();
        NonTicketedEvent event = eventState.createNonTicketedEvent(
                entertainmentProvider,
                "World of Dance",
                EventType.Dance
        );
        event.cancel();
        assertEquals(EventStatus.CANCELLED, eventState.findEventByNumber(1).getStatus());
    }

    @Test
    @DisplayName("Finding An Event With Invalid Event Number")
    void findInvalidEventNumber() {
        EventState eventState = new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();
         TicketedEvent event = eventState.createTicketedEvent(
                 entertainmentProvider,
                 "Choir Night",
                 EventType.Music,
                 5.00,
                 300
         );
         assertEquals(null, eventState.findEventByNumber(2));
    }
}
