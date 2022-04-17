import command.*;
import controller.Controller;
import logging.Logger;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterEntertainmentProviderRepSystemTests {
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

    private static void registerEntProvider2(Controller controller) {
        ArrayList<String> orgReps = new ArrayList<String>();
        orgReps.add("Andy One");
        orgReps.add("Andy Two");
        ArrayList<String> orgRepsEmails = new ArrayList<String>();
        orgRepsEmails.add("andrew1@ed.ac.uk");
        orgRepsEmails.add("andrew2@hotmail.com");

        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Andrew Incorporated",
                "AN7 1AB",
                "a0e0@gmail.com",
                "Andrew ZERO",
                "a0e0@gmail.com",
                "Andrew7",
                orgReps,
                orgRepsEmails
        ));
    }

    private static void registerEntProvider3(Controller controller) {
        ArrayList<String> orgReps = new ArrayList<String>();
        ArrayList<String> orgRepsEmails = new ArrayList<String>();

        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Lone Wolf co",
                "the middle of nowhere",
                "lonewolf@gmail.com",
                "mr wolf",
                "lone-wolf@gmail.com",
                "2w3SEDrFTGYHujn",
                orgReps,
                orgRepsEmails
        ));
    }

    private static void updateEntProvider1(Controller controller) {
        ArrayList<String> orgReps = new ArrayList<String>();
        orgReps.add("Jane Org");
        orgReps.add("Josh Org");
        ArrayList<String> orgRepsEmails = new ArrayList<String>();
        orgRepsEmails.add("janeorg@gmail.com");
        orgRepsEmails.add("joshorg@gmail.com");

        controller.runCommand(new UpdateEntertainmentProviderProfileCommand(
                "iloveorgs",
                "entertainmentOrgOne",
                "22 Org Street, OrgCity, Scotland",
                "orgone@gmail.com",
                "Joe Org",
                "joeorg@gmail.com",
                "ihateorgs",
                orgReps,
                orgRepsEmails
        ));
    }

    @Test
    void RegisterEntertainmentProvider1() {
        Controller controller = new Controller();

        // I think this line (and all others like it) are redundant.
        // registerEntProvider1(controller);
        // controller.runCommand(new LogoutCommand());

        ArrayList<String> orgReps = new ArrayList<String>();
        orgReps.add("Jane Org");
        orgReps.add("Josh Org");
        ArrayList<String> orgRepsEmails = new ArrayList<String>();
        orgRepsEmails.add("janeorg@gmail.com");
        orgRepsEmails.add("joshorg@gmail.com");

        RegisterEntertainmentProviderCommand cmd = new RegisterEntertainmentProviderCommand(
                "entertainmentOrgOne",
                "22 Org Street, OrgCity, Scotland",
                "orgone@gmail.com",
                "Joe Org",
                "joeorg@gmail.com",
                "iloveorgs",
                orgReps,
                orgRepsEmails
        );

        controller.runCommand(cmd);
        EntertainmentProvider ep = cmd.getResult();

        assertEquals("entertainmentOrgOne", ep.getOrgName());
        assertEquals("22 Org Street, OrgCity, Scotland", ep.getOrgAddress());
        assertEquals("orgone@gmail.com", ep.getPaymentAccountEmail());
        assertEquals("joeorg@gmail.com", ep.getEmail());
        assertEquals(true, ep.checkPasswordMatch("iloveorgs"));
    }

    @Test
    void RegisterEntertainmentProvider2() {
        Controller controller = new Controller();

        // registerEntProvider2(controller);
        // controller.runCommand(new LogoutCommand());

        ArrayList<String> orgReps = new ArrayList<String>();
        orgReps.add("Andy One");
        orgReps.add("Andy Two");
        ArrayList<String> orgRepsEmails = new ArrayList<String>();
        orgRepsEmails.add("andrew1@ed.ac.uk");
        orgRepsEmails.add("andrew2@hotmail.com");

        RegisterEntertainmentProviderCommand cmd = new RegisterEntertainmentProviderCommand(
                "Andrew Incorporated",
                "AN7 1AB",
                "a0e0@gmail.com",
                "Andrew ZERO",
                "a0e0@gmail.com",
                "Andrew7",
                orgReps,
                orgRepsEmails
        );

        controller.runCommand(cmd);
        EntertainmentProvider ep = cmd.getResult();

        assertEquals("Andrew Incorporated", ep.getOrgName());
        assertEquals("AN7 1AB", ep.getOrgAddress());
        assertEquals("a0e0@gmail.com", ep.getPaymentAccountEmail());
        assertEquals("a0e0@gmail.com", ep.getEmail());
        assertEquals(true, ep.checkPasswordMatch("Andrew7"));
    }

    @Test
    void RegisterEntertainmentProvider3() {
        Controller controller = new Controller();

        // registerEntProvider3(controller);
        // controller.runCommand(new LogoutCommand());

        ArrayList<String> orgReps = new ArrayList<String>();
        ArrayList<String> orgRepsEmails = new ArrayList<String>();

        RegisterEntertainmentProviderCommand cmd = new RegisterEntertainmentProviderCommand(
                "Lone Wolf co",
                "the middle of nowhere",
                "lonewolf@gmail.com",
                "mr wolf",
                "lone-wolf@gmail.com",
                "2w3SEDrFTGYHujn",
                orgReps,
                orgRepsEmails
        );

        controller.runCommand(cmd);
        EntertainmentProvider ep = cmd.getResult();

        assertEquals("Lone Wolf co", ep.getOrgName());
        assertEquals("the middle of nowhere", ep.getOrgAddress());
        assertEquals("lonewolf@gmail.com", ep.getPaymentAccountEmail());
        assertEquals("lone-wolf@gmail.com", ep.getEmail());
        assertEquals(true, ep.checkPasswordMatch("2w3SEDrFTGYHujn"));
    }

    @Test
    void ChangeEntertainmentProvider1Details() {
        Controller controller = new Controller();

        registerEntProvider1(controller);
        // updateEntProvider1(controller);
        // controller.runCommand(new LogoutCommand());

        ArrayList<String> orgReps = new ArrayList<String>();
        orgReps.add("Jane Org");
        orgReps.add("Josh Org");
        ArrayList<String> orgRepsEmails = new ArrayList<String>();
        orgRepsEmails.add("janeorg@gmail.com");
        orgRepsEmails.add("joshorg@gmail.com");

        UpdateEntertainmentProviderProfileCommand cmd1 = new UpdateEntertainmentProviderProfileCommand(
                "iloveorgs",
                "entertainmentOrgOne",
                "22 Org Street, OrgCity, Scotland",
                "orgone@gmail.com",
                "Joe Org",
                "joeorg@gmail.com",
                "ihateorgs",
                orgReps,
                orgRepsEmails
        );

        controller.runCommand(cmd1);
        Boolean successfulUpdate = (Boolean) cmd1.getResult();

        assertEquals(true, successfulUpdate);
    }

}
