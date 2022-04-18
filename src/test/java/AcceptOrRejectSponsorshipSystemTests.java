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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AcceptOrRejectSponsorshipSystemTests {
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
        orgReps.add("Jane Org");
        orgReps.add("Josh Org");
        ArrayList<String> orgRepsEmails = new ArrayList<String>();
        orgRepsEmails.add("janeorg@gmail.com");
        orgRepsEmails.add("joshorg@gmail.com");

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

    private static void govRepLogIn(Controller controller) {
        controller.runCommand(new LoginCommand("margaret.thatcher@gov.uk", "The Good times  "));
    }

    private static void EntProviderLogIn(Controller controller) {
        controller.runCommand(new LoginCommand("joeorg@gmail.com", "iloveorgs"));
    }

    private static void logOutCommand(Controller controller) {
        controller.runCommand(new LogoutCommand());
    }

    @Test
    void acceptSponsorshipRequest() {
        Controller controller = new Controller();

        // create event, set sponsorship request
        // respond to request

        registerEntProvider1(controller);

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                true
        );
        controller.runCommand(cmd1);
        long eventNum = cmd1.getResult();

        controller.runCommand(new AddEventPerformanceCommand(
                eventNum,
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

        logOutCommand(controller);

        govRepLogIn(controller);

        RespondSponsorshipCommand cmd2 = new RespondSponsorshipCommand(
                1,
                10
        );

        controller.runCommand(cmd2);
        boolean successfulResponse = cmd2.getResult();

        logOutCommand(controller);

        EntProviderLogIn(controller);

        ListEventsCommand cmd3 = new ListEventsCommand(false, false);
        controller.runCommand(cmd3);
        List<Event> listofevents = cmd3.getResult();

        logOutCommand(controller);

        Event testEvent = listofevents.get(0);

        assertEquals(true, successfulResponse);

        assertEquals(true, ((TicketedEvent)testEvent).isSponsored());
        assertNotEquals(((TicketedEvent)testEvent).getOriginalTicketPrice(),
                        ((TicketedEvent)testEvent).getDiscountedTicketPrice()
        );
        assertEquals(0.9,
                     (((TicketedEvent)testEvent).getDiscountedTicketPrice() / ((TicketedEvent)testEvent).getOriginalTicketPrice())
        );

    }

    @Test
    void rejectSponsorshipRequest() {
        Controller controller = new Controller();

        // create event, set sponsorship request
        // respond to request (deny)

        registerEntProvider1(controller);

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                true
        );
        controller.runCommand(cmd1);
        long eventNum = cmd1.getResult();

        controller.runCommand(new AddEventPerformanceCommand(
                eventNum,
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

        logOutCommand(controller);

        govRepLogIn(controller);

        RespondSponsorshipCommand cmd2 = new RespondSponsorshipCommand(
                1,
                0
        );

        controller.runCommand(cmd2);
        boolean successfulResponse = cmd2.getResult();

        logOutCommand(controller);

        EntProviderLogIn(controller);

        ListEventsCommand cmd3 = new ListEventsCommand(false, false);
        controller.runCommand(cmd3);
        List<Event> listofevents = cmd3.getResult();

        logOutCommand(controller);

        Event testEvent = listofevents.get(0);

        assertEquals(true, successfulResponse);

        assertEquals(false, ((TicketedEvent)testEvent).isSponsored());
        assertEquals(((TicketedEvent)testEvent).getOriginalTicketPrice(),
                     ((TicketedEvent)testEvent).getDiscountedTicketPrice()
        );

    }

}
