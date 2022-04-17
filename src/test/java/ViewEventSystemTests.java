import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewEventSystemTests {
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
                "Movie Marathon",
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
    void viewAvailableTicketNumber() {
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
        assertEquals(EventType.Theatre, ticketedEvent.getType());
        assertEquals(300, ticketedEvent.getNumTickets());
        assertEquals(15, ticketedEvent.getOriginalTicketPrice());
        assertEquals(false, ticketedEvent.isSponsored());
        assertEquals("Trial Entertainment Org", ticketedEvent.getOrganiser().getOrgName());
        assertEquals("middle of nowhere", ticketedEvent.getOrganiser().getOrgAddress());
    }

    @Test
    void viewPerformanceDetails() {
        Controller controller = new Controller();
        registerEntertainmentProvider(controller);
        arrangeTicketedEvent1Performance(controller);
        controller.runCommand(new LogoutCommand());
        registerConsumer(controller);
        ListEventsCommand cmd = new ListEventsCommand(false, true);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
        TicketedEvent ticketedEvent = (TicketedEvent) eventList.get(0);
        Collection<EventPerformance> performanceList = ticketedEvent.getPerformances();
        EventPerformance performance1 = ticketedEvent.getPerformanceByNumber(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");

        assertEquals(1, performance1.getPerformanceNumber());
        assertEquals(LocalDateTime.now().plusMonths(1).plusHours(3).format(formatter), performance1.getEndDateTime().format(formatter));
        assertEquals(LocalDateTime.now().plusMonths(1).format(formatter), performance1.getStartDateTime().format(formatter));
        assertEquals(ticketedEvent, performance1.getEvent());
        assertEquals(false, performance1.hasSocialDistancing());
        assertEquals(true, performance1.hasAirFiltration());
        assertEquals(false, performance1.isOutdoors());
        assertEquals(250, performance1.getCapacityLimit());
        assertEquals(250, performance1.getVenueSize());
    }

    @Test
    void viewListOfPerformances() {
        Controller controller = new Controller();
        registerEntertainmentProvider(controller);
        arrangeNonTicketedEvent2Performance(controller);
        controller.runCommand(new LogoutCommand());
        registerConsumer(controller);
        ListEventsCommand cmd = new ListEventsCommand(false, true);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
        NonTicketedEvent event = (NonTicketedEvent) eventList.get(0);
        Collection<EventPerformance> performanceList = event.getPerformances();
        EventPerformance performance1 = (EventPerformance) performanceList.toArray()[0];
        EventPerformance performance2 = (EventPerformance) performanceList.toArray()[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");

        assertEquals(1, eventList.size());
        assertEquals(1, performance1.getPerformanceNumber());
        assertEquals(2, performance2.getPerformanceNumber());
        assertEquals(1, performance1.getEvent().getEventNumber());
        assertEquals(1, performance2.getEvent().getEventNumber());
        assertEquals(LocalDateTime.now().plusWeeks(2).format(formatter), performance1.getStartDateTime().format(formatter));
        assertEquals(LocalDateTime.now().plusWeeks(2).plusHours(5).format(formatter), performance1.getEndDateTime().format(formatter));
        assertEquals(LocalDateTime.now().plusWeeks(1).format(formatter), performance2.getStartDateTime().format(formatter));
        assertEquals(LocalDateTime.now().plusWeeks(1).plusHours(2).format(formatter), performance2.getEndDateTime().format(formatter));
    }
}
