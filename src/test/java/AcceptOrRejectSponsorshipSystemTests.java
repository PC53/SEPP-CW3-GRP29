import command.CancelEventCommand;
import command.CreateTicketedEventCommand;
import command.RegisterEntertainmentProviderCommand;
import command.RespondSponsorshipCommand;
import controller.Controller;
import logging.Logger;
import model.EntertainmentProvider;
import model.EventType;
import model.SponsorshipRequest;
import model.TicketedEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.ArrayList;

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

    @Test
    void acceptSponsorshipRequest() {
        Controller controller = new Controller();

        // create event, set sponsorship request
        // respond to request

        ArrayList<String> orgReps = new ArrayList<String>();
        orgReps.add("Jane Org");
        orgReps.add("Josh Org");
        ArrayList<String> orgRepsEmails = new ArrayList<String>();
        orgRepsEmails.add("janeorg@gmail.com");
        orgRepsEmails.add("joshorg@gmail.com");

        RegisterEntertainmentProviderCommand cmd0 = new RegisterEntertainmentProviderCommand(
                "entertainmentOrgOne",
                "22 Org Street, OrgCity, Scotland",
                "orgone@gmail.com",
                "Joe Org",
                "joeorg@gmail.com",
                "iloveorgs",
                orgReps,
                orgRepsEmails
        );

        controller.runCommand(cmd0);
        EntertainmentProvider ep = cmd0.getResult();

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                true
        );
        controller.runCommand(cmd1);
        long eventNum = cmd1.getResult();

        TicketedEvent testEvent = new TicketedEvent(
                eventNum,
                ep,
                "test ticketed event 1",
                EventType.Movie,
                10,
                9999
        );

        SponsorshipRequest testSponsorshipRequest = new SponsorshipRequest(1, testEvent);

        RespondSponsorshipCommand cmd3 = new RespondSponsorshipCommand(
                testSponsorshipRequest.getRequestNumber(),
                10
        );

        boolean successfulResponse = cmd3.getResult();
        assertEquals(true, successfulResponse);

        assertEquals(true, testEvent.isSponsored());
        assertNotEquals(testEvent.getOriginalTicketPrice(), testEvent.getDiscountedTicketPrice());

        // could also find a way to check ticket discount is exactly 10%
    }

    @Test
    void rejectSponsorshipRequest() {
        Controller controller = new Controller();

        // create event, set sponsorship request
        // respond to request (deny)

        ArrayList<String> orgReps = new ArrayList<String>();
        orgReps.add("Jane Org");
        orgReps.add("Josh Org");
        ArrayList<String> orgRepsEmails = new ArrayList<String>();
        orgRepsEmails.add("janeorg@gmail.com");
        orgRepsEmails.add("joshorg@gmail.com");

        RegisterEntertainmentProviderCommand cmd0 = new RegisterEntertainmentProviderCommand(
                "entertainmentOrgOne",
                "22 Org Street, OrgCity, Scotland",
                "orgone@gmail.com",
                "Joe Org",
                "joeorg@gmail.com",
                "iloveorgs",
                orgReps,
                orgRepsEmails
        );

        controller.runCommand(cmd0);
        EntertainmentProvider ep = cmd0.getResult();

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                true
        );
        controller.runCommand(cmd1);
        long eventNum = cmd1.getResult();

        TicketedEvent testEvent = new TicketedEvent(
                eventNum,
                ep,
                "test ticketed event 1",
                EventType.Movie,
                10,
                9999
        );

        SponsorshipRequest testSponsorshipRequest = new SponsorshipRequest(1, testEvent);

        RespondSponsorshipCommand cmd3 = new RespondSponsorshipCommand(
                testSponsorshipRequest.getRequestNumber(),
                0
        );

        boolean successfulResponse = cmd3.getResult();
        assertEquals(true, successfulResponse);

        assertEquals(false, testEvent.isSponsored());
        assertEquals(testEvent.getOriginalTicketPrice(), testEvent.getDiscountedTicketPrice());

    }

}
