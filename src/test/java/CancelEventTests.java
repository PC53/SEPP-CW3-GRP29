import command.CancelEventCommand;
import command.CreateNonTicketedEventCommand;
import command.CreateTicketedEventCommand;
import command.RegisterEntertainmentProviderCommand;
import controller.Controller;
import logging.Logger;
import model.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class CancelEventTests {
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

    private static void createTicketedEvent1(Controller controller) {
        controller.runCommand(new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        ));
    }

    @Test
    void cancelTicketedEventTest () {
        Controller controller = new Controller();

        registerEntProvider1(controller);

        CreateTicketedEventCommand cmd1 = new CreateTicketedEventCommand(
                "ticketed event one",
                EventType.Movie,
                9999,
                10,
                false
        );

        controller.runCommand(cmd1);
        long eventNum = cmd1.getResult();

        CancelEventCommand cmd2 = new CancelEventCommand(
                eventNum,
                "test event deletion"
        );

        controller.runCommand(cmd2);
        boolean successfulRemoval = (boolean) cmd2.getResult();

        assertEquals(true, successfulRemoval);

        // should also check that tickets are refunded?
    }

    @Test
    void cancelNonTicketedEventTest () {
        Controller controller = new Controller();

        registerEntProvider1(controller);

        CreateNonTicketedEventCommand cmd1 = new CreateNonTicketedEventCommand(
                "non ticketed event one",
                EventType.Movie
        );

        controller.runCommand(cmd1);
        long eventNum = cmd1.getResult();

        CancelEventCommand cmd2 = new CancelEventCommand(
                eventNum,
                "test event deletion"
        );

        controller.runCommand(cmd2);
        boolean successfulRemoval = (boolean) cmd2.getResult();

        assertEquals(true, successfulRemoval);

    }

}
