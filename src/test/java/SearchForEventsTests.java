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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchForEventsTests {
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
                "abcdef",
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

    private static void UpdateConsumer2Preferences(Controller controller) {
        ConsumerPreferences consumer2Preferences = new ConsumerPreferences();
        consumer2Preferences.preferSocialDistancing = true;
        consumer2Preferences.preferOutdoorsOnly = false;
        consumer2Preferences.preferAirFiltration = true;
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
    }

    private static void register3EntProvider(Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Jackson Incorporated",
                "EH8 123",
                "jackson123@gmail.com",
                "Jackson Mike",
                "jacksonmike@gmail.com",
                "abcdefg",
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
        controller.runCommand(new LoginCommand("juliustemitope17@gmail.com", "abcdef"));
    }

    private static void consumer2LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("helladioscarman35@ed.ac.uk", "carmen123"));
    }

    private static void EP1LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("jacksonmike@gmail.com", "abcdefg"));
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
                LocalDateTime.now().plusMonths(3),
                LocalDateTime.now().plusMonths(3).plusHours(3),
                performers,
                false,
                true,
                true,
                50,
                500
        );
        controller.runCommand(performanceCmd2);
    }

    private static long createTicketedEvent2(Controller controller) {
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Event Two",
                EventType.Music,
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
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusHours(3),
                performers,
                true,
                true,
                true,
                1200,
                1200
        );
        controller.runCommand(performanceCmd1);
        AddEventPerformanceCommand performanceCmd2 = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(3),
                performers,
                false,
                true,
                true,
                500,
                500
        );
        controller.runCommand(performanceCmd2);
    }

    private static long createTicketedEvent3(Controller controller) {
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Event Three",
                EventType.Sports,
                2,
                100,
                false
        );
        controller.runCommand(eventCmd);
        return eventCmd.getResult();
    }

    private static void createPerformancesForTicketedEvent3(Controller controller, long eventNumber) {
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

    @Test
    void test1() {
        Controller controller = new Controller();
        register2Consumers(controller);
        register3EntProvider(controller);
        EP1LogIn(controller);
        Long eventNumber1 = createTicketedEvent1(controller);
        createPerformancesForTicketedEvent1(controller, eventNumber1);
        logOutCommand(controller);
        EP1LogIn(controller);
        long eventNumber2 = createTicketedEvent2(controller);
        createPerformancesForTicketedEvent2(controller, eventNumber2);
        logOutCommand(controller);
        EP1LogIn(controller);
        long eventNumber3 = createTicketedEvent3(controller);
        createPerformancesForTicketedEvent3(controller, eventNumber3);
        logOutCommand(controller);
        consumer1LogIn(controller);
        ListEventsCommand cmd = new ListEventsCommand(true, true);
        controller.runCommand(cmd);
        List<Event> selectedEvent = cmd.getResult();
        ConsumerPreferences initialOne = new ConsumerPreferences();
        assertEquals(1, selectedEvent.size());
        assertEquals(3, selectedEvent.get(0).getEventNumber());
        assertEquals(EventStatus.ACTIVE, selectedEvent.get(0).getStatus());
        assertEquals(initialOne.preferAirFiltration,selectedEvent.get(0).getPerformanceByNumber(6).hasAirFiltration());
        assertEquals(initialOne.preferOutdoorsOnly,selectedEvent.get(0).getPerformanceByNumber(6).isOutdoors());
        assertEquals(initialOne.preferSocialDistancing,selectedEvent.get(0).getPerformanceByNumber(6).hasSocialDistancing());
        assertTrue(initialOne.preferredMaxCapacity > selectedEvent.get(0).getPerformanceByNumber(6).getCapacityLimit());
        assertTrue(initialOne.preferredMaxVenueSize > selectedEvent.get(0).getPerformanceByNumber(6).getVenueSize());
    }

    //To be continued!
}
