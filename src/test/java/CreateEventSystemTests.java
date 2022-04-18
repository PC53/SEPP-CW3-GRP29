import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateEventSystemTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void registerEntProvider1(Controller controller) {
        ArrayList<String> orgReps = new ArrayList<String>();
        ArrayList<String> orgRepsEmails = new ArrayList<String>();

        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "entertainmentOrgOne",
                "22 Org Street, OrgCity, Scotland",
                "orgone@gmail.com",
                "Joe Org",
                "joeorg@gmail.com",
                "iloveorgs",
                orgReps,
                orgRepsEmails
        ));
    }

    private static void registerEntProvider2(Controller controller) {
        ArrayList<String> orgReps = new ArrayList<String>();
        ArrayList<String> orgRepsEmails = new ArrayList<String>();

        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "entertainmentOrgTwo",
                "34 Org Street, OrgCity, Scotland",
                "orgtwo@gmail.com",
                "James Org",
                "joeorgtwo@gmail.com",
                "iloveorgs",
                orgReps,
                orgRepsEmails
        ));
    }

    private static void loginEntProvider1(Controller controller) {
        controller.runCommand(new LoginCommand("joeorg@gmail.com", "iloveorgs"));
    }

    private static void loginEntProvider2(Controller controller) {
        controller.runCommand(new LoginCommand("joeorgtwo@gmail.com", "iloveorgs"));
    }

    private static void createTicketedEvent1(Controller controller) {
        controller.runCommand(new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        ));
    }

    private static long createNonTicketedEvent(Controller controller) {
        CreateNonTicketedEventCommand cmd = new CreateNonTicketedEventCommand(
                "non ticketed event one",
                EventType.Movie
        );

        controller.runCommand(cmd);
        long eventNumber1 = cmd.getResult();

        ArrayList<String> performers = new ArrayList<String>();
        performers.add("performer one");

        controller.runCommand(new AddEventPerformanceCommand(
                eventNumber1,
                "test performance 1",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        ));

        return eventNumber1;

    }

    private static long createEventWithPerformance(Controller controller) {
        CreateTicketedEventCommand eventCmd1 = new CreateTicketedEventCommand(
                "event with performance 1",
                EventType.Movie,
                123456,
                25,
                false
        );
        controller.runCommand(eventCmd1);
        long eventNumber1 = eventCmd1.getResult();

        ArrayList<String> performers = new ArrayList<String>();
        performers.add("performer one");

        controller.runCommand(new AddEventPerformanceCommand(
                eventNumber1,
                "test performance 1",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        ));

        return eventNumber1;
    }

    private static void createEventWithMultiplePerformances1(Controller controller) {
        CreateTicketedEventCommand eventCmd1 = new CreateTicketedEventCommand(
                "event with multiple performances 1",
                EventType.Movie,
                123456,
                25,
                false
        );
        controller.runCommand(eventCmd1);
        long eventNumber1 = eventCmd1.getResult();

        ArrayList<String> performers = new ArrayList<String>();
        performers.add("performer one");

        controller.runCommand(new AddEventPerformanceCommand(
                eventNumber1,
                "test performance 1",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        ));

        controller.runCommand(new AddEventPerformanceCommand(
                eventNumber1,
                "test performance 2",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        ));
    }

    private static void createEventWithMultiplePerformances2(Controller controller) {
        CreateTicketedEventCommand eventCmd1 = new CreateTicketedEventCommand(
                "event with multiple performances 2",
                EventType.Movie,
                123456,
                25,
                false
        );
        controller.runCommand(eventCmd1);
        long eventNumber1 = eventCmd1.getResult();

        ArrayList<String> performers = new ArrayList<String>();
        performers.add("performer one");

        controller.runCommand(new AddEventPerformanceCommand(
                eventNumber1,
                "test performance 1",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        ));

        controller.runCommand(new AddEventPerformanceCommand(
                eventNumber1,
                "test performance 2",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        ));
    }


    @Test
    void creatingTicketedEvent () {
        Controller controller = new Controller();
        registerEntProvider1(controller);

        Long eventID = createEventWithPerformance(controller);

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        controller.runCommand(new LogoutCommand());

        assertEquals(eventID, listofevents.get(0).getEventNumber());
        assertTrue(listofevents.get(0) instanceof TicketedEvent);
    }

    @Test
    void creatingNonTicketedEvent () {
        Controller controller = new Controller();
        registerEntProvider1(controller);

        long eventID = createNonTicketedEvent(controller);

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        controller.runCommand(new LogoutCommand());

        assertEquals(eventID, listofevents.get(0).getEventNumber());
        assertTrue(listofevents.get(0) instanceof NonTicketedEvent);
    }

    @Test
    void creatingAnEventAfterLoggingIn () {
        Controller controller = new Controller();

        registerEntProvider1(controller);
        controller.runCommand(new LogoutCommand());

        loginEntProvider1(controller);

        Long eventID = createEventWithPerformance(controller);

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        controller.runCommand(new LogoutCommand());

        assertEquals(eventID, listofevents.get(0).getEventNumber());
    }

    @Test
    void addingPerformanceToAnEvent () {
        Controller controller = new Controller();
        registerEntProvider1(controller);

        Long eventID = createEventWithPerformance(controller);

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        ArrayList<String> performers = new ArrayList<>();
        performers.add("performer one");

        EventPerformance performance = new EventPerformance(
                1,
                listofevents.get(0),
                "test performance 1",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        );

        listofevents.get(0).addPerformance(performance);

        Collection<EventPerformance> eventPerformances = listofevents.get(0).getPerformances();

        controller.runCommand(new LogoutCommand());

        assertTrue(eventPerformances.contains(performance));
    }

    // This test is not complete!!!
    @Test
    void creatingAnEventWithASponsorshipRequest () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        //createEventWithPerformance(controller);
        controller.runCommand(new LogoutCommand());
        //createEventWithSponsorshipRequest(controller);
    }

    @Test
    void addingMultiplePerformancesToAnEvent () {
        Controller controller = new Controller();
        registerEntProvider1(controller);

        Long eventID = createEventWithPerformance(controller);

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        List<EventPerformance> performances = new ArrayList<EventPerformance>();

        ArrayList<String> performers = new ArrayList<String>();
        performers.add("performer one");

        EventPerformance performance1 = new EventPerformance(
                1,
                listofevents.get(0),
                "test performance 1",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        );
        listofevents.get(0).addPerformance(performance1);
        performances.add(performance1);

        EventPerformance performance2 = new EventPerformance(
                2,
                listofevents.get(0),
                "test performance 2",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        );
        listofevents.get(0).addPerformance(performance2);
        performances.add(performance2);

        Collection<EventPerformance> eventPerformances = listofevents.get(0).getPerformances();

        for (EventPerformance performance : performances) {
            assertTrue(eventPerformances.contains(performance));
        }
    }

    @Test
    void creatingMultipleEvents () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        controller.runCommand(new LogoutCommand());
        registerEntProvider2(controller);
        controller.runCommand(new LogoutCommand());

        loginEntProvider1(controller);

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        );

        controller.runCommand(cmd1);
        Long event1ID = cmd1.getResult();

        controller.runCommand(new AddEventPerformanceCommand(
                event1ID,
                "Leith as usual",
                LocalDateTime.of(2030, 3, 20, 4, 20),
                LocalDateTime.of(2030, 3, 20, 6, 45),
                List.of("The same musician"),
                true,
                true,
                true,
                Integer.MAX_VALUE,
                Integer.MAX_VALUE
        ));

        controller.runCommand(new LogoutCommand());
        loginEntProvider2(controller);

        CreateNonTicketedEventCommand cmd2 = new CreateNonTicketedEventCommand(
                "non ticketed event one",
                EventType.Movie
        );

        controller.runCommand(cmd2);
        Long event2ID = cmd2.getResult();

        controller.runCommand(new AddEventPerformanceCommand(
                event2ID,
                "Leith as usual",
                LocalDateTime.of(2030, 3, 20, 4, 20),
                LocalDateTime.of(2030, 3, 20, 6, 45),
                List.of("The same musician"),
                true,
                true,
                true,
                Integer.MAX_VALUE,
                Integer.MAX_VALUE
        ));

        controller.runCommand(new LogoutCommand());

        ListEventsCommand cmd3 = new ListEventsCommand(false, false);
        controller.runCommand(cmd3);
        List<Event> listofevents = cmd3.getResult();

        assertEquals(event1ID, listofevents.get(0).getEventNumber());
        assertEquals(event2ID, listofevents.get(1).getEventNumber());
    }

    @Test
    void creatingMultipleEventsWithMultiplePerformances () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        controller.runCommand(new LogoutCommand());
        registerEntProvider2(controller);
        controller.runCommand(new LogoutCommand());

        loginEntProvider1(controller);

        Long event1ID = createEventWithPerformance(controller);

        ListEventsCommand cmd2 = new ListEventsCommand(true, false);
        controller.runCommand(cmd2);
        List<Event> listofevents1 = cmd2.getResult();

        List<EventPerformance> performances1 = new ArrayList<EventPerformance>();

        ArrayList<String> performers = new ArrayList<String>();
        performers.add("performer one");

        EventPerformance performance1 = new EventPerformance(
                1,
                listofevents1.get(0),
                "test performance 1",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        );
        listofevents1.get(0).addPerformance(performance1);
        performances1.add(performance1);

        EventPerformance performance2 = new EventPerformance(
                2,
                listofevents1.get(0),
                "test performance 2",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        );
        listofevents1.get(0).addPerformance(performance2);
        performances1.add(performance2);

        controller.runCommand(new LogoutCommand());
        loginEntProvider2(controller);

        Long event2ID = createNonTicketedEvent(controller);

        ListEventsCommand cmd4 = new ListEventsCommand(true, false);
        controller.runCommand(cmd4);
        List<Event> listofevents2 = cmd4.getResult();

        List<EventPerformance> performances2 = new ArrayList<EventPerformance>();

        EventPerformance performance3 = new EventPerformance(
                3,
                listofevents2.get(0),
                "test performance 3",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        );
        listofevents2.get(0).addPerformance(performance3);
        performances2.add(performance3);

        EventPerformance performance4 = new EventPerformance(
                4,
                listofevents2.get(0),
                "test performance 4",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        );
        listofevents2.get(0).addPerformance(performance4);
        performances2.add(performance4);

        ListEventsCommand cmd5 = new ListEventsCommand(false, false);
        controller.runCommand(cmd5);
        List<Event> listofevents = cmd5.getResult();

        assertEquals(event1ID, listofevents.get(0).getEventNumber());
        assertEquals(event2ID, listofevents.get(1).getEventNumber());

        Collection<EventPerformance> event1Performances = listofevents.get(0).getPerformances();

        for (EventPerformance performance : performances1) {
            assertTrue(event1Performances.contains(performance));
        }

        Collection<EventPerformance> event2Performances = listofevents.get(1).getPerformances();

        for (EventPerformance performance : performances2) {
            assertTrue(event2Performances.contains(performance));
        }
    }

}
