import command.*;
import controller.Controller;
import logging.Logger;
import model.Event;
import model.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

    // Registering 3 consumers
    private static void register3Consumers(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Ayato Kamisato",
                "ayato@gmail.com",
                "0723478190",
                "Olderw@t3rd0dE",
                "ayato@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterConsumerCommand(
                "Yan Fei",
                "yanfei88@ed.ac.uk",
                "0119378525",
                "F1reL@wLady",
                "yanfei@yahoo.com"
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterConsumerCommand(
                "Childe Tartaglia",
                "ajax@hotmail.com",
                "0294718741",
                "Hydr0 boii1ii",
                "ajax@hotmail.com"
        ));
        controller.runCommand(new LogoutCommand());
    }

    // Consumer log ins
    private static void loginConsumer1(Controller controller) {
        controller.runCommand(new LoginCommand("ayato@gmail.com", "Olderw@t3rd0dE"));
    }

    private static void loginConsumer2 (Controller controller) {
        controller.runCommand(new LoginCommand("yanfei@ed.ac.uk", "F1reL@wLady"));
    }

    private static void loginConsumer3 (Controller controller) {
        controller.runCommand(new LoginCommand("ajax@hotmail.com", "Hydr0 boii1ii"));
    }

    // Create non ticketed event and ticketed event
    private static void createMusicalProvider2Events (Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "UoE Theatre Committee",
                "Summerhall",
                "eusasocieties@gmail.com",
                "John Jones",
                "jj123@hotmail.com",
                "indiana jone's brother",
                Collections.emptyList(),
                Collections.emptyList()
        ));
        CreateTicketedEventCommand event1 = new CreateTicketedEventCommand(
                "Dear Evan Hansen",
                EventType.Theatre,
                200,
                13.82,
                false
        );
        controller.runCommand(event1);
        long event1Number = event1.getResult();
        controller.runCommand(new AddEventPerformanceCommand(
                event1Number,
                "Castle in the sky",
                LocalDateTime.now().plusWeeks(1),
                LocalDateTime.now().plusWeeks(1).plusHours(2),
                Collections.emptyList(),
                true,
                true,
                false,
                200,
                300
        ));

        CreateNonTicketedEventCommand event2 = new CreateNonTicketedEventCommand(
                "I dont know the name",
                EventType.Theatre
        );
        controller.runCommand(event2);
        long event2Number = event2.getResult();
        controller.runCommand(new AddEventPerformanceCommand(
                event2Number,
                "River Styx",
                LocalDateTime.now().minusDays(3),
                LocalDateTime.now().minusDays(3).plusHours(4),
                List.of("stranger 1", "stranger 2"),
                false,
                false,
                true,
                500,
                560
        ));

        controller.runCommand(new LogoutCommand());
    }

    private static void createCinemaProvider2Events (Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "U8",
                "middle of nowhere",
                "dontanswer@gmail.com",
                "nameless guy",
                "guynoname@gmail.com",
                "someone give me a name please",
                List.of("overworked", "sleep deprived"),
                List.of("overworked@gmail.com", "sleep-deprived@gmail.com")
        ));

        CreateTicketedEventCommand movie1 = new CreateTicketedEventCommand(
                "The Eternals",
                EventType.Movie,
                200,
                7.20,
                false
        );
        controller.runCommand(movie1);
        long movie1Number = movie1.getResult();
        controller.runCommand(new AddEventPerformanceCommand(
                movie1Number,
                "middle of nowhere",
                LocalDateTime.now().plusWeeks(2),
                LocalDateTime.now().plusWeeks(2).plusHours(3),
                Collections.emptyList(),
                true,
                true,
                false,
                150,
                200
        ));

        CreateNonTicketedEventCommand movie2 = new CreateNonTicketedEventCommand(
                "Movie Marathon",
                EventType.Movie
        );
        controller.runCommand(movie2);
        long movie2Number = movie2.getResult();
        controller.runCommand(new AddEventPerformanceCommand(
                movie2Number,
                "Appleton Tower",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusDays(2),
                List.of("a committee team"),
                false,
                true,
                false,
                550,
                600
        ));
        controller.runCommand(new AddEventPerformanceCommand(
                movie2Number,
                "George Square",
                LocalDateTime.now().plusMonths(1),
                LocalDateTime.now().plusMonths(1).plusDays(2),
                List.of("a committee team"),
                false,
                true,
                false,
                300,
                350
        ));

        controller.runCommand(new LogoutCommand());
    }

    // get a list of events
    private static void getListOfEvents (Controller controller) {
        controller.runCommand(new ListEventsCommand(true, true));
    }

    // view a particular event
    private static void getEventDetails (long eventNumber) {

    }

    @Test
    void viewNonTicketedEvents() {
        Controller controller = new Controller();
        createCinemaProvider2Events(controller);
        createMusicalProvider2Events(controller);
        register3Consumers(controller);
        loginConsumer1(controller);
        ListEventsCommand cmd = new ListEventsCommand(true, true);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
//        assertEquals
    }
}
