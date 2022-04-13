import command.*;
import controller.Controller;
import logging.Logger;
import model.EventType;
import model.SponsorshipRequest;
import model.TicketedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreateEventTests {
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
        controller.runCommand(new LogoutCommand());
    }

    @Test
    void creatingNonTicketedEvent () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        createNonTicketedEvent1(controller);
        controller.runCommand(new LogoutCommand());
    }

    @Test
    void creatingAnEventAfterLoggingIn () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        controller.runCommand(new LogoutCommand());
        loginEntProvider1(controller);
        createTicketedEvent1(controller);
        controller.runCommand(new LogoutCommand());
    }

    @Test
    void addingPerformanceToAnEvent () {
        Controller controller = new Controller();
        registerEntProvider1(controller);
        createEventWithPerformance(controller);
        controller.runCommand(new LogoutCommand());
    }

    // This test is not complete!
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
    }

}
