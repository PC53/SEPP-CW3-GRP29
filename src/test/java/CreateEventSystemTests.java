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

    private static void createTicketedEvent1(Controller controller) {
        controller.runCommand(new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        ));
    }

    private static void createNonTicketedEvent1(Controller controller) {
        controller.runCommand(new CreateNonTicketedEventCommand(
                "non ticketed event one",
                EventType.Movie
        ));
    }

    private static void loginEntProvider1(Controller controller) {
        controller.runCommand(new LoginCommand("orgone@gmail.com", "iloveorgs"));
    }

    private static void createEventWithPerformance(Controller controller) {
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


    /*

    private static void createEventWithSponsorshipRequest(Controller controller) {
        CreateTicketedEventCommand eventCmd1 = new CreateTicketedEventCommand(
                "event with sponsored performance 1",
                EventType.Movie,
                123456,
                25,
                true
        );

        controller.runCommand(eventCmd1);
        long eventNumber1 = eventCmd1.getResult();

        ArrayList<String> performers = new ArrayList<String>();
        performers.add("performer one");

        TicketedEvent tEvent = new TicketedEvent(
                eventNumber1,
                "event with sponsored performance 1",
                EventType.Movie,
                123456,
                25,
                true
        );


        SponsorshipRequest sponorReq = new SponsorshipRequest(1, eventCmd1);
    }

    */



    @Test
    void creatingTicketedEvent () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        createTicketedEvent1(controller);

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        );

        controller.runCommand(cmd1);
        Long eventID = cmd1.getResult();

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        assertEquals(eventID, listofevents.get(0).getEventNumber());

    }

    @Test
    void creatingNonTicketedEvent () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        createNonTicketedEvent1(controller);
        controller.runCommand(new LogoutCommand());

        CreateNonTicketedEventCommand cmd1 = new CreateNonTicketedEventCommand(
                "non ticketed event one",
                EventType.Movie
        );

        controller.runCommand(cmd1);
        Long eventID = cmd1.getResult();

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        assertEquals(eventID, listofevents.get(0).getEventNumber());
    }

    @Test
    void creatingAnEventAfterLoggingIn () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        controller.runCommand(new LogoutCommand());
        loginEntProvider1(controller);
        createTicketedEvent1(controller);
        controller.runCommand(new LogoutCommand());

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        );

        controller.runCommand(cmd1);
        Long eventID = cmd1.getResult();

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        assertEquals(eventID, listofevents.get(0).getEventNumber());
    }

    @Test
    void addingPerformanceToAnEvent () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        createEventWithPerformance(controller);
        controller.runCommand(new LogoutCommand());

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        );

        controller.runCommand(cmd1);
        Long eventID = cmd1.getResult();

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        ArrayList<String> performers = new ArrayList<String>();
        performers.add("performer one");

        EventPerformance performance = new EventPerformance(
                eventID,
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

        List<EventPerformance> eventPerformances = (List<EventPerformance>) listofevents.get(0).getPerformances();

        assertEquals(performance, eventPerformances.get(0));
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
        createEventWithMultiplePerformances1(controller);
        controller.runCommand(new LogoutCommand());

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        );

        controller.runCommand(cmd1);
        Long eventID = cmd1.getResult();

        ListEventsCommand cmd2 = new ListEventsCommand(false, false);
        controller.runCommand(cmd2);
        List<Event> listofevents = cmd2.getResult();

        List<EventPerformance> performances = new ArrayList<EventPerformance>();

        ArrayList<String> performers = new ArrayList<String>();
        performers.add("performer one");

        EventPerformance performance1 = new EventPerformance(
                eventID,
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
        performances.add(performance1);
        EventPerformance performance2 = new EventPerformance(
                eventID,
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
        performances.add(performance2);
        List<EventPerformance> eventPerformances = (List<EventPerformance>) listofevents.get(0).getPerformances();

        assertEquals(performances, eventPerformances);
    }

    @Test
    void creatingMultipleEvents () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        createTicketedEvent1(controller);
        controller.runCommand(new LogoutCommand());
        registerEntProvider2(controller);
        createNonTicketedEvent1(controller);
        controller.runCommand(new LogoutCommand());

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        );

        CreateNonTicketedEventCommand cmd2 = new CreateNonTicketedEventCommand(
                "non ticketed event one",
                EventType.Movie
        );

        controller.runCommand(cmd1);
        Long event1ID = cmd1.getResult();

        controller.runCommand(cmd2);
        Long event2ID = cmd2.getResult();

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
        createEventWithMultiplePerformances1(controller);
        controller.runCommand(new LogoutCommand());
        registerEntProvider2(controller);
        createEventWithMultiplePerformances2(controller);
        controller.runCommand(new LogoutCommand());

        // This one is gonna take ages to code...

        /*
        EventPerformance testperformance1 = new EventPerformance(
                eventID,
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

        EventPerformance testperformance2 = new EventPerformance(
                eventID,
                listofevents.get(0),
                "test performance 2",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(8),
                performers,
                false,
                true,
                true,
                3000,
                3000
        );

        assertEquals();

         */
    }

}
