import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewEventTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    // ----------Testing viewing events------------
    // Register test users (consumers & EPs), login test users (consumers & EPs), create events (ticketed & non ticketed),
    // create performances, get list of events, view events (ticket availability,
    // event number, EP organiser, title, type, ticket price,

    private static void registerConsumer(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Indiana Jones",
                "indianajones@gmail.com",
                "0779238472",
                "i am the main character",
                "indianajones@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static void registerEntertainmentProvider(Controller controller) {
        ArrayList<String> orgReps = new ArrayList<>();
        orgReps.add("Jane Org");
        orgReps.add("Josh Org");
        ArrayList<String> orgRepsEmails = new ArrayList<>();
        orgRepsEmails.add("janeorg@gmail.com");
        orgRepsEmails.add("joshorg@gmail.com");

        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Trial Entertainment Org",
                "middle of nowhere",
                "i-am-broke@gmail.com",
                "Im Quitting",
                "jobless@gmail.com",
                "i question life choices",
                orgReps,
                orgRepsEmails
        ));
    }

    private static void arrangeTicketedEvent(Controller controller) {
        controller.runCommand(new CreateTicketedEventCommand(
                "Hamilton Musical",
                EventType.Theatre,
                300,
                15,
                false
        ));
    }

    private static void arrangeNonTicketedEvent(Controller controller) {
        controller.runCommand(new CreateNonTicketedEventCommand(
                "Charity Run",
                EventType.Sports
        ));
    }

    private static void arrangeTicketedEvent1Performance(Controller controller) {
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Movie Marathons",
                EventType.Movie,
                200,
                10,
                false
        );
        controller.runCommand(eventCmd);
        long eventNumber = eventCmd.getResult();

        ArrayList<String> performers = new ArrayList<>();
        performers.add("workers");
        AddEventPerformanceCommand performanceCmd = new AddEventPerformanceCommand(
                eventNumber,
                "Howl's Moving Castle",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(3),
                performers,
                false,
                true,
                false,
                250,
                250
        );
        controller.runCommand(performanceCmd);
//        long performaneNumber = performanceCmd.getResult().getPerformanceNumber();
    }

    private static void arrangeNonTicketedEvent2Performance(Controller controller) {
        CreateNonTicketedEventCommand eventCmd = new CreateNonTicketedEventCommand(
                "Busking Event",
                EventType.Music
        );
        controller.runCommand(eventCmd);
        long eventNum = eventCmd.getResult();

        ArrayList<String> performers = new ArrayList<>();
        performers.add("Singers");
        performers.add("Musicians");
        AddEventPerformanceCommand performanceCmd1 = new AddEventPerformanceCommand(
                eventNum,
                "Bristol Square",
                LocalDateTime.now().plusWeeks(2),
                LocalDateTime.now().plusWeeks(2).plusHours(5),
                performers,
                false,
                false,
                true,
                500,
                500
        );
        controller.runCommand(performanceCmd1);

        AddEventPerformanceCommand performanceCmd2 = new AddEventPerformanceCommand(
                eventNum,
                "The Meadows",
                LocalDateTime.now().plusWeeks(1),
                LocalDateTime.now().plusWeeks(1).plusHours(2),
                performers,
                false,
                false,
                true,
                1000,
                1000
        );
        controller.runCommand(performanceCmd2);
    }

    @Test
    void viewNonTicketedEvent() {
        Controller controller = new Controller();
        registerEntertainmentProvider(controller);
        arrangeNonTicketedEvent(controller);
        controller.runCommand(new LogoutCommand());
        registerConsumer(controller);
        ListEventsCommand cmd = new ListEventsCommand(false, false);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
        Event nonTickedEvent = eventList.get(0);
        assertEquals("Charity Run", nonTickedEvent.getTitle());
        assertEquals(EventType.Sports, nonTickedEvent.getType());
    }

    @Test
    void viewTicketNumber() {
        Controller controller = new Controller();
        registerEntertainmentProvider(controller);
        arrangeTicketedEvent(controller);
        controller.runCommand(new LogoutCommand());
        registerConsumer(controller);
        ListEventsCommand cmd = new ListEventsCommand(false, true);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
        TicketedEvent ticketedEvent = (TicketedEvent) eventList.get(0);
        int tickets = ticketedEvent.getNumTickets();
        assertEquals(300, tickets);
    }

    @Test
    void viewTicketedEvent() {
        Controller controller = new Controller();
        registerEntertainmentProvider(controller);
        arrangeTicketedEvent(controller);
        controller.runCommand(new LogoutCommand());
        registerConsumer(controller);
        ListEventsCommand cmd = new ListEventsCommand(false, true);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
        TicketedEvent ticketedEvent = (TicketedEvent) eventList.get(0);

        assertEquals("Hamilton Musical", ticketedEvent.getTitle());

    }

//    @Test
//    void viewEventDetails1() {
//        Controller controller = new Controller();
//        createMusicalProvider2Events(controller);
//        register3Consumers(controller);
//        loginConsumer1(controller);
//        ListEventsCommand cmd = new ListEventsCommand(false, false);
//        controller.runCommand(cmd);
//        List<Event> eventList = cmd.getResult();
//        Event event1 = eventList.get(0);
//        Collection<EventPerformance> performanceList = event1.getPerformances();
//        System.out.println(performanceList.toString());
//
//
//        assertEquals("Dear Evan Hansen", event1.getTitle());
//        assertEquals(1, event1.getEventNumber());
//        assertEquals(EventType.Theatre, event1.getType());
//        assertEquals("UoE Theatre Committee",event1.getOrganiser().getOrgName());
//        assertEquals("Summerhall", event1.getOrganiser().getOrgAddress());
//        assertEquals();
//    }

//    @Test
//    void viewEventDetails() {
//        Controller controller = new Controller();
//        createMusicalProvider2Events(controller);
//        register3Consumers(controller);
//        loginConsumer1(controller);
//        ListEventsCommand cmd = new ListEventsCommand(false, true);
//        controller.runCommand(cmd);
//        List<Event> eventList = cmd.getResult();
//        Event event1 = eventList.get(0);
//        long eventNumber = event1.getEventNumber();
//        EntertainmentProvider oraniser = event1.getOrganiser();
//        String title = event1.getTitle();
//        EventType eventType = event1.getType();
//        Collection<EventPerformance> eventPerformances = event1.getPerformances();
////        int numberOfTickets = (TicketedEvent) event1.
//
//        assertEquals(1, eventNumber);
////        assertEquals("UoE Theatre Committee", oraniser);
//        assertEquals("Dear Evan Hansen", title);
//        assertEquals(EventType.Theatre, eventType);
////        assertEquals(Collections.emptyList(), eventPerformances);
//
//    }
}
