import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
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

    // Create ticketed event
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
//        controller.runCommand(new AddEventPerformanceCommand(
//                event1Number,
//                "Castle in the sky",
//                LocalDateTime.now().plusWeeks(1),
//                LocalDateTime.now().plusWeeks(1).plusHours(2),
//                Collections.emptyList(),
//                true,
//                true,
//                false,
//                200,
//                300
//        ));

//        CreateTicketedEventCommand event2 = new CreateTicketedEventCommand(
//                "I dont know the name",
//                EventType.Theatre,
//                500,
//                10.93,
//                false
//        );
//        controller.runCommand(event2);
//        long event2Number = event2.getResult();
//        controller.runCommand(new AddEventPerformanceCommand(
//                event2Number,
//                "River Styx",
//                LocalDateTime.now().minusDays(3),
//                LocalDateTime.now().minusDays(3).plusHours(4),
//                List.of("stranger 1", "stranger 2"),
//                false,
//                false,
//                true,
//                500,
//                560
//        ));

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

//    // get a list of events
//    private static void getListOfEvents (Controller controller) {
//        controller.runCommand(new ListEventsCommand(true, true));
//
//    }
//
//    // view a particular event
//    private static void getEventDetails (long eventNumber) {
//
//    }

    @Test
    void viewEvent1Number() {
        Controller controller = new Controller();
        createMusicalProvider2Events(controller);
        register3Consumers(controller);
        loginConsumer1(controller);
        ListEventsCommand cmd = new ListEventsCommand(false, true);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
        Event event1 = eventList.get(0);
        long eventNum = event1.getEventNumber();
        assertEquals(1, eventNum);
    }

    @Test
    void viewEvent1Name() {
        Controller controller = new Controller();
        createMusicalProvider2Events(controller);
        register3Consumers(controller);
        loginConsumer1(controller);
        ListEventsCommand cmd = new ListEventsCommand(false, true);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
        Event event1 = eventList.get(0);
        String eventName = event1.getTitle();

        assertEquals(eventName, "Dear Evan Hansen");
    }

    @Test
    void viewEvent1Type() {
        Controller controller = new Controller();
        createMusicalProvider2Events(controller);
        register3Consumers(controller);
        loginConsumer1(controller);
        ListEventsCommand cmd = new ListEventsCommand(false, true);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
        Event event1 = eventList.get(0);
        EventType eventType = event1.getType();

        assertEquals(eventType, EventType.Theatre);
    }

    @Test
    void viewEvent1Organiser() {
        Controller controller = new Controller();
        createMusicalProvider2Events(controller);
        register3Consumers(controller);
        loginConsumer1(controller);
        ListEventsCommand cmd = new ListEventsCommand(false, true);
        controller.runCommand(cmd);
        List<Event> eventList = cmd.getResult();
        Event event1 = eventList.get(0);
        EntertainmentProvider organiser = event1.getOrganiser();

        assertEquals(new EntertainmentProvider("UoE Theatre Committee", "Summerhall",
                "eusasocieties@gmail.com",  "John Jones", "jj123@hotmail.com",
                "indiana jone's brother", Collections.emptyList(), Collections.emptyList()), organiser);
    }

    @Test
    void viewEventTicketAvailability() {

    }

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
