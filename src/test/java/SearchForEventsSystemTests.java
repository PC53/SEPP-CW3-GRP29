import command.*;
import controller.Controller;
import logging.Logger;
import model.ConsumerPreferences;
import model.Event;
import model.EventStatus;
import model.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchForEventsSystemTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void register2Consumers(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Julius Temitope",
                "juliustemitope17@gmail.com",
                "07438462934",
                "abcdefgh",
                "juliustemitope7@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterConsumerCommand(
                "Helladios Carman",
                "helladioscarman35@ed.ac.uk",
                "07430264816",
                "carmen123",
                "helladioscarman731@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static ConsumerPreferences updateConsumer2Preferences(Controller controller) {
        ConsumerPreferences consumer2Preferences = new ConsumerPreferences();
        consumer2Preferences.preferSocialDistancing = true;
        consumer2Preferences.preferOutdoorsOnly = true;
        consumer2Preferences.preferAirFiltration = false;
        consumer2Preferences.preferredMaxCapacity = 100;
        consumer2Preferences.preferredMaxVenueSize = 1000;
        controller.runCommand( new UpdateConsumerProfileCommand(
                "carmen123",
                "helladioscarman35@ed.ac.uk",
                "Helladios Carman",
                "07430264816",
                "carmen123",
                "helladioscarman731@gmail.com",
                consumer2Preferences
        ));
        return consumer2Preferences;
    }

    private static void register3EntProvider(Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Jackson Incorporated",
                "EH8 123",
                "jackson123@gmail.com",
                "Jackson Mike",
                "jacksonmike@gmail.com",
                "abcdefgh",
                new ArrayList<String>(),
                new ArrayList<String>()
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Peter Incorporated",
                "EH8 234",
                "peter234@gmail.com",
                "Peter Yao",
                "peteryao@gmail.com",
                "abcd1234",
                new ArrayList<String>(),
                new ArrayList<String>()
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Russell Incorporated",
                "EH8 345",
                "russell345@gmail.com",
                "Russell Harry",
                "russellharry@gmail.com",
                "12345678",
                new ArrayList<String>(),
                new ArrayList<String>()
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static void logOutCommand(Controller controller) {
        controller.runCommand(new LogoutCommand());
    }

    private static void consumer1LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("juliustemitope17@gmail.com", "abcdefgh"));
    }

    private static void consumer2LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("helladioscarman35@ed.ac.uk", "carmen123"));
    }

    private static void EP1LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("jacksonmike@gmail.com", "abcdefgh"));
    }

    private static void EP2LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("peteryao@gmail.com", "abcd1234"));
    }

    private static void EP3LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("russellharry@gmail.com", "12345678"));
    }


    private static long createTicketedEvent1(Controller controller) {
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Event One",
                EventType.Movie,
                50,
                100,
                false
        );
        controller.runCommand(eventCmd);
        return eventCmd.getResult();
    }

    private static void createPerformancesForTicketedEvent1(Controller controller, long eventNumber) {
        ArrayList<String> performers = new ArrayList<String>();
        performers.add("Performer A");
        performers.add("Performer B");
        AddEventPerformanceCommand performanceCmd1 = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(3),
                performers,
                false,
                false,
                false,
                120,
                1200
        );
        controller.runCommand(performanceCmd1);
        AddEventPerformanceCommand performanceCmd2 = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(3),
                LocalDateTime.now().plusMonths(3).plusHours(3),
                performers,
                true,
                false,
                true,
                101,
                1001
        );
        controller.runCommand(performanceCmd2);
    }

    private static long createNonTicketedEvent(Controller controller) {
        CreateNonTicketedEventCommand eventCmd = new CreateNonTicketedEventCommand(
                "Event two",
                EventType.Music
        );
        controller.runCommand(eventCmd);
        return eventCmd.getResult();
    }

    private static void createPerformancesForNonTicketedEvent(Controller controller, long eventNumber) {
        ArrayList<String> performers = new ArrayList<String>();
        performers.add("Performer A");
        performers.add("Performer B");
        AddEventPerformanceCommand performanceCmd1 = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(3),
                performers,
                true,
                false,
                true,
                99,
                999
        );
        controller.runCommand(performanceCmd1);
        AddEventPerformanceCommand performanceCmd2 = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(3),
                performers,
                false,
                false,
                true,
                50,
                500
        );
        controller.runCommand(performanceCmd2);
    }

    private static long createTicketedEvent2(Controller controller) {
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Event Three",
                EventType.Sports,
                50,
                100,
                false
        );
        controller.runCommand(eventCmd);
        return eventCmd.getResult();
    }

    private static void createPerformancesForTicketedEvent2(Controller controller, long eventNumber) {
        ArrayList<String> performers = new ArrayList<String>();
        performers.add("Performer A");
        performers.add("Performer B");
        AddEventPerformanceCommand performanceCmd1 = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(3),
                LocalDateTime.now().plusMonths(3).plusHours(2),
                performers,
                true,
                true,
                true,
                120,
                1200
        );
        controller.runCommand(performanceCmd1);
        AddEventPerformanceCommand performanceCmd2 = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(4),
                LocalDateTime.now().plusMonths(4).plusHours(2),
                performers,
                false,
                false,
                false,
                50,
                500
        );
        controller.runCommand(performanceCmd2);
    }

    private static void cancelEvent(Controller controller, long eventNum) {
        CancelEventCommand cmd = new CancelEventCommand(
                eventNum,
                "test event deletion"
        );
        controller.runCommand(cmd);
    }

    @Test
    void consumerSearchActiveEventsTests() {
        // Create users in the system
        Controller controller = new Controller();
        register2Consumers(controller);
        register3EntProvider(controller);

        // Add events for EP1
        EP1LogIn(controller);
        long eventNumber1 = createTicketedEvent1(controller);
        createPerformancesForTicketedEvent1(controller, eventNumber1);
        logOutCommand(controller);

        // Add events for EP2
        EP2LogIn(controller);
        long eventNumber2 = createNonTicketedEvent(controller);
        createPerformancesForNonTicketedEvent(controller, eventNumber2);
        logOutCommand(controller);

        // Add events for EP3
        EP3LogIn(controller);
        long eventNumber3 = createTicketedEvent2(controller);
        createPerformancesForTicketedEvent2(controller, eventNumber3);
        logOutCommand(controller);

        // Tests for consumer 1 with initial preferences
        consumer1LogIn(controller);
        ListEventsCommand cmd1 = new ListEventsCommand(true, true);
        controller.runCommand(cmd1);
        List<Event> selectedEvent1 = cmd1.getResult();
        ConsumerPreferences initialOne = new ConsumerPreferences();
        assertEquals(2, selectedEvent1.size());
        assertEquals(1, selectedEvent1.get(0).getEventNumber());
        assertEquals(EventStatus.ACTIVE, selectedEvent1.get(0).getStatus());
        assertEquals(initialOne.preferAirFiltration,selectedEvent1.get(0).getPerformanceByNumber(1).hasAirFiltration());
        assertEquals(initialOne.preferOutdoorsOnly,selectedEvent1.get(0).getPerformanceByNumber(1).isOutdoors());
        assertEquals(initialOne.preferSocialDistancing,selectedEvent1.get(0).getPerformanceByNumber(1).hasSocialDistancing());
        assertTrue(initialOne.preferredMaxCapacity > selectedEvent1.get(0).getPerformanceByNumber(1).getCapacityLimit());
        assertTrue(initialOne.preferredMaxVenueSize > selectedEvent1.get(0).getPerformanceByNumber(1).getVenueSize());
        assertEquals(3, selectedEvent1.get(1).getEventNumber());
        assertEquals(EventStatus.ACTIVE, selectedEvent1.get(1).getStatus());
        assertEquals(initialOne.preferAirFiltration,selectedEvent1.get(1).getPerformanceByNumber(6).hasAirFiltration());
        assertEquals(initialOne.preferOutdoorsOnly,selectedEvent1.get(1).getPerformanceByNumber(6).isOutdoors());
        assertEquals(initialOne.preferSocialDistancing,selectedEvent1.get(1).getPerformanceByNumber(6).hasSocialDistancing());
        assertTrue(initialOne.preferredMaxCapacity > selectedEvent1.get(1).getPerformanceByNumber(6).getCapacityLimit());
        assertTrue(initialOne.preferredMaxVenueSize > selectedEvent1.get(1).getPerformanceByNumber(6).getVenueSize());
        logOutCommand(controller);

        // Tests for consumer 2 with updated preferences
        consumer2LogIn(controller);
        ConsumerPreferences currentPreferences = updateConsumer2Preferences(controller);
        logOutCommand(controller);

        consumer2LogIn(controller);
        ListEventsCommand cmd2 = new ListEventsCommand(true, true);
        controller.runCommand(cmd2);
        List<Event> selectedEvent2 = cmd2.getResult();
        assertEquals(1, selectedEvent2.size());
        assertEquals(2, selectedEvent2.get(0).getEventNumber());
        assertEquals(EventStatus.ACTIVE, selectedEvent2.get(0).getStatus());
        assertEquals(currentPreferences.preferAirFiltration,selectedEvent2.get(0).getPerformanceByNumber(3).hasAirFiltration());
        assertEquals(currentPreferences.preferOutdoorsOnly,selectedEvent2.get(0).getPerformanceByNumber(3).isOutdoors());
        assertEquals(currentPreferences.preferSocialDistancing,selectedEvent2.get(0).getPerformanceByNumber(3).hasSocialDistancing());
        assertTrue(currentPreferences.preferredMaxCapacity > selectedEvent2.get(0).getPerformanceByNumber(3).getCapacityLimit());
        assertTrue(currentPreferences.preferredMaxVenueSize > selectedEvent2.get(0).getPerformanceByNumber(3).getVenueSize());
    }

    @Test
    void consumerSearchCancelledEventsTests() {
        // Create users in the system
        Controller controller = new Controller();
        register2Consumers(controller);
        register3EntProvider(controller);

        // Add events for EP1
        EP1LogIn(controller);
        long eventNumber1 = createTicketedEvent1(controller);
        createPerformancesForTicketedEvent1(controller, eventNumber1);
        logOutCommand(controller);

        // Add events for EP2
        EP2LogIn(controller);
        long eventNumber2 = createNonTicketedEvent(controller);
        createPerformancesForNonTicketedEvent(controller, eventNumber2);
        logOutCommand(controller);

        // Add events for EP3
        EP3LogIn(controller);
        long eventNumber3 = createTicketedEvent2(controller);
        createPerformancesForTicketedEvent2(controller, eventNumber3);
        cancelEvent(controller, eventNumber3);
        logOutCommand(controller);

        // Tests for consumer 1 with initial preferences excluding cancelled events
        consumer1LogIn(controller);
        ListEventsCommand cmd1 = new ListEventsCommand(true, true);
        controller.runCommand(cmd1);
        List<Event> selectedEvent1 = cmd1.getResult();
        ConsumerPreferences initialOne = new ConsumerPreferences();
        assertEquals(1, selectedEvent1.size());
        assertEquals(1, selectedEvent1.get(0).getEventNumber());
        assertEquals(EventStatus.ACTIVE, selectedEvent1.get(0).getStatus());
        assertEquals(initialOne.preferAirFiltration,selectedEvent1.get(0).getPerformanceByNumber(1).hasAirFiltration());
        assertEquals(initialOne.preferOutdoorsOnly,selectedEvent1.get(0).getPerformanceByNumber(1).isOutdoors());
        assertEquals(initialOne.preferSocialDistancing,selectedEvent1.get(0).getPerformanceByNumber(1).hasSocialDistancing());
        assertTrue(initialOne.preferredMaxCapacity > selectedEvent1.get(0).getPerformanceByNumber(1).getCapacityLimit());
        assertTrue(initialOne.preferredMaxVenueSize > selectedEvent1.get(0).getPerformanceByNumber(1).getVenueSize());
        logOutCommand(controller);

        // Tests for consumer 1 with initial preferences including cancelled events
        consumer1LogIn(controller);
        ListEventsCommand cmd2 = new ListEventsCommand(true, false);
        controller.runCommand(cmd2);
        List<Event> selectedEvent2 = cmd2.getResult();
        assertEquals(2, selectedEvent2.size());
        assertEquals(1, selectedEvent2.get(0).getEventNumber());
        assertEquals(EventStatus.ACTIVE, selectedEvent2.get(0).getStatus());
        assertEquals(initialOne.preferAirFiltration,selectedEvent2.get(0).getPerformanceByNumber(1).hasAirFiltration());
        assertEquals(initialOne.preferOutdoorsOnly,selectedEvent2.get(0).getPerformanceByNumber(1).isOutdoors());
        assertEquals(initialOne.preferSocialDistancing,selectedEvent2.get(0).getPerformanceByNumber(1).hasSocialDistancing());
        assertTrue(initialOne.preferredMaxCapacity > selectedEvent2.get(0).getPerformanceByNumber(1).getCapacityLimit());
        assertTrue(initialOne.preferredMaxVenueSize > selectedEvent2.get(0).getPerformanceByNumber(1).getVenueSize());
        assertEquals(3, selectedEvent2.get(1).getEventNumber());
        assertEquals(EventStatus.CANCELLED, selectedEvent2.get(1).getStatus());
        assertEquals(initialOne.preferAirFiltration,selectedEvent2.get(1).getPerformanceByNumber(6).hasAirFiltration());
        assertEquals(initialOne.preferOutdoorsOnly,selectedEvent2.get(1).getPerformanceByNumber(6).isOutdoors());
        assertEquals(initialOne.preferSocialDistancing,selectedEvent2.get(1).getPerformanceByNumber(6).hasSocialDistancing());
        assertTrue(initialOne.preferredMaxCapacity > selectedEvent2.get(1).getPerformanceByNumber(6).getCapacityLimit());
        assertTrue(initialOne.preferredMaxVenueSize > selectedEvent2.get(1).getPerformanceByNumber(6).getVenueSize());

        // Tests for finding all the events including cancelled events
        ListEventsCommand cmd3 = new ListEventsCommand(false, true);
        controller.runCommand(cmd3);
        List<Event> selectedEvent3 = cmd3.getResult();
        assertEquals(2, selectedEvent3.size());

        // Tests for finding all the events excluding cancelled events
        ListEventsCommand cmd4 = new ListEventsCommand(false, false);
        controller.runCommand(cmd4);
        List<Event> selectedEvent4 = cmd4.getResult();
        assertEquals(3, selectedEvent4.size());
        logOutCommand(controller);
    }

    @Test
    void EPSearchEventsTests() {
        // Create users in the system
        Controller controller = new Controller();
        register2Consumers(controller);
        register3EntProvider(controller);

        // Add events for EP1
        EP1LogIn(controller);
        long eventNumber1 = createTicketedEvent1(controller);
        createPerformancesForTicketedEvent1(controller, eventNumber1);
        logOutCommand(controller);

        // Add events for EP2
        EP2LogIn(controller);
        long eventNumber2 = createNonTicketedEvent(controller);
        createPerformancesForNonTicketedEvent(controller, eventNumber2);
        logOutCommand(controller);

        // Add events for EP3
        EP3LogIn(controller);
        long eventNumber3 = createTicketedEvent2(controller);
        createPerformancesForTicketedEvent2(controller, eventNumber3);
        logOutCommand(controller);

        // Tests for Entertainment Provider 1
        EP1LogIn(controller);
        ListEventsCommand cmd1 = new ListEventsCommand(true, true);
        controller.runCommand(cmd1);
        List<Event> selectedEvent1 = cmd1.getResult();
        assertEquals(1, selectedEvent1.size());
        assertEquals(1, selectedEvent1.get(0).getEventNumber());

        // Cancel the event for EP1
        cancelEvent(controller, eventNumber1);

        // Tests for Entertainment Provider 1 excluding cancelled events
        controller.runCommand(cmd1);
        List<Event> selectedEvent2 = cmd1.getResult();
        assertTrue(selectedEvent2.isEmpty());

        // Tests for Entertainment Provider 1 including cancelled events
        ListEventsCommand cmd2 = new ListEventsCommand(true, false);
        controller.runCommand(cmd2);
        List<Event> selectedEvent3 = cmd2.getResult();
        assertEquals(1, selectedEvent3.size());
        assertEquals(1, selectedEvent3.get(0).getEventNumber());
        logOutCommand(controller);

        // Tests for finding all the events including cancelled events
        EP2LogIn(controller);
        ListEventsCommand cmd3 = new ListEventsCommand(false, false);
        controller.runCommand(cmd3);
        List<Event> selectedEvent4 = cmd3.getResult();
        assertEquals(3, selectedEvent4.size());

        // Tests for finding all the events excluding cancelled events
        ListEventsCommand cmd4 = new ListEventsCommand(false, true);
        controller.runCommand(cmd4);
        List<Event> selectedEvent5 = cmd4.getResult();
        assertEquals(2, selectedEvent5.size());
        logOutCommand(controller);
    }
}
