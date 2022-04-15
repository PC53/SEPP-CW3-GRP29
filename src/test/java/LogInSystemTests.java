import command.*;
import controller.Context;
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


public class LogInSystemTests {

    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }


    // -------------TESTING CONSUMER LOGINS:--------------
    // REGISTER USERS, LOGIN, CHECK LOGGED IN, UPDATE PASSWORD, LOGOUT, LOG BACK IN

    // Registering Consumers
    private static void registerConsumer1(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Adam Apple",
                "adamapple7@hotmail.co.uk",
                "071740213850",
                "aapple",
                "adamapple7@hotmail.co.uk"
        ));
    }

    private static void logoutConsumer1(Controller controller) {
        controller.runCommand(new LogoutCommand());
    }

    private static void registerConsumer2(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Bernard Bee",
                "berny@inf.ed.ac.uk",
                "04417492071",
                "bernyBurns",
                "berny@aol.com"
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static void registerConsumer3(Controller controller) {

        controller.runCommand(new RegisterConsumerCommand(
                "Charlie Chapel",
                "char-chap@gmail.com",
                "-",
                "char chaps chill claps",
                "char-chap@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static void registerConsumer4(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Derek Dirkly",
                "DerekD@gmail.com",
                "-",
                "n84hd4dbn8djHDS738n",
                "DerekD@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
    }


    // Login consumers:
    private static void loginConsumer1(Controller controller) {
        controller.runCommand(new LoginCommand("adamapple7@hotmail.co.uk", "aapple"));
    }

    private static void loginConsumer2(Controller controller) {
        controller.runCommand(new LoginCommand("berny@inf.ed.ac.uk", "bernyBurns"));
    }

    private static void loginConsumer3(Controller controller) {
        controller.runCommand(new LoginCommand("char-chap@gmail.com", "char chaps chill claps"));
    }

    private static void loginConsumer4(Controller controller) {
        controller.runCommand(new LoginCommand("DerekD@gmail.com", "n84hd4dbn8djHDS738n"));
    }

    // check consumers are logged in:

    /*
    private static void checkRegisteredIsLoggedIn(Controller controller) {

    }
    */

    private static void updateConsumer1(Controller controller) {
        controller.runCommand(new UpdateConsumerProfileCommand(
                "aaple",
                "adamapple7@hotmail.co.uk",
                "Apple Adam",
                "071740210000",
                "asdfghjk",
                "adamapple7new@hotmail.co.uk",
                new ConsumerPreferences()));
        controller.runCommand(new LogoutCommand());
    }

    private static void setConsumer2Preferences(Controller controller) {
        ConsumerPreferences cpreferences = new ConsumerPreferences();
        cpreferences.preferSocialDistancing = true;
        cpreferences.preferOutdoorsOnly = true;
        cpreferences.preferAirFiltration = true;
        cpreferences.preferredMaxCapacity = 100;
        cpreferences.preferredMaxVenueSize = 1000;
        controller.runCommand(new UpdateConsumerProfileCommand(
                "bernyBurns",
                "berny@inf.ed.ac.uk",
                "Bernard Bee",
                "04417492071",
                "bernyBurns",
                "berny@inf.ed.ac.uk",
                cpreferences
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static void registerEntertainmentProvider1(Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Test Organisation",
                "Org Avenue",
                "testorg@gmail.com",
                "John Doe",
                "johndoe@gmail.com",
                "jdoe",
                List.of("Jane Doe", "James Doe"),
                List.of("janedoe@gmail.com", "jamesdoe@gmail.com")
        ));
        controller.runCommand(new LogoutCommand());
    }



    // Large test to test the logging in of different consumer's with different details.
    // also testing the logging in of consumers after their details change.
    // This may need to be split into smaller tests.

    // also may need to add some assert equals to areas of the test to check if their logged in
    @Test
    void loginConsumersTest() {
        Controller controller = new Controller();

        // registering users and logging them out
        registerConsumer1(controller);
        logoutConsumer1(controller);

        registerConsumer2(controller);
        registerConsumer3(controller);
        registerConsumer4(controller);

        // logging in consumers
        loginConsumer1(controller);
        controller.runCommand(new LogoutCommand());
        loginConsumer2(controller);
        controller.runCommand(new LogoutCommand());
        loginConsumer3(controller);
        controller.runCommand(new LogoutCommand());
        loginConsumer4(controller);
        controller.runCommand(new LogoutCommand());

        // updating consumer details then relogging in
        loginConsumer1(controller);
        updateConsumer1(controller);
        controller.runCommand(new LogoutCommand());
        loginConsumer1(controller);
        controller.runCommand(new LogoutCommand());

        // setting consumer preferences then relogging in
        loginConsumer2(controller);
        setConsumer2Preferences(controller);
        controller.runCommand(new LogoutCommand());
        loginConsumer2(controller);
        controller.runCommand(new LogoutCommand());

    }

    @Test
    void checkCurrentUserIsLoggedIn() {
        Controller controller = new Controller();

        registerConsumer1(controller);
        logoutConsumer1(controller);

        loginConsumer1(controller);

        LoginCommand cmd = new LoginCommand("adamapple7@hotmail.co.uk","aapple");
        controller.runCommand(cmd);
        User loggedUser = (User) cmd.getResult();

        assertEquals("adamapple7@hotmail.co.uk", loggedUser.getEmail());
        assertEquals("adamapple7@hotmail.co.uk", loggedUser.getPaymentAccountEmail());
        assertEquals(true, loggedUser.checkPasswordMatch("aapple"));

        controller.runCommand(new LogoutCommand());
    }

    @Test
    void checkConsumerDetailsHaveChanged() {
        Controller controller = new Controller();

        registerConsumer1(controller);
        logoutConsumer1(controller);

        loginConsumer1(controller);
        updateConsumer1(controller);

        LoginCommand cmd = new LoginCommand("adamapple7@hotmail.co.uk","aapple");
        controller.runCommand(cmd);
        User loggedUser = (User) cmd.getResult();

        assertEquals("adamapple7@hotmail.co.uk", loggedUser.getEmail());
        assertEquals("adamapple7new@hotmail.co.uk", loggedUser.getPaymentAccountEmail());
        assertEquals(true, loggedUser.checkPasswordMatch("asdfghjk"));

        controller.runCommand(new LogoutCommand());
    }

    @Test
    void settingConsumerPreferences() {
        Controller controller = new Controller();

        registerConsumer2(controller);

        loginConsumer2(controller);
        setConsumer2Preferences(controller);

        LoginCommand cmd = new LoginCommand("berny@inf.ed.ac.uk","bernyBurns");
        controller.runCommand(cmd);
        Consumer loggedUser = (Consumer) cmd.getResult();

        ConsumerPreferences testpreferences = new ConsumerPreferences();
        testpreferences.preferSocialDistancing = true;
        testpreferences.preferOutdoorsOnly = true;
        testpreferences.preferAirFiltration = true;
        testpreferences.preferredMaxCapacity = 100;
        testpreferences.preferredMaxVenueSize = 1000;

        assertEquals(testpreferences.preferSocialDistancing, loggedUser.getPreferences().preferSocialDistancing);
        assertEquals(testpreferences.preferOutdoorsOnly, loggedUser.getPreferences().preferOutdoorsOnly);
        assertEquals(testpreferences.preferAirFiltration, loggedUser.getPreferences().preferAirFiltration);
        assertEquals(testpreferences.preferredMaxCapacity, loggedUser.getPreferences().preferredMaxCapacity);
        assertEquals(testpreferences.preferredMaxVenueSize, loggedUser.getPreferences().preferredMaxVenueSize);
    }

    // -----------TESTING ENTERTAINMENT REP LOGIN-----------
    @Test
    void loginEntertainmentRepresentative() {
        Controller controller = new Controller();

        registerEntertainmentProvider1(controller);

        LoginCommand cmd = new LoginCommand("johndoe@gmail.com","jdoe");
        controller.runCommand(cmd);
        User loggedUser = (User) cmd.getResult();

        assertEquals("johndoe@gmail.com", loggedUser.getEmail());
        assertEquals("testorg@gmail.com", loggedUser.getPaymentAccountEmail());
        assertEquals(true, loggedUser.checkPasswordMatch("jdoe"));
    }

    // ----------TESTING GOV REP LOGIN------------
    @Test
    void loginGovernmentRepresentative() {
        Controller controller = new Controller();

        LoginCommand cmd = new LoginCommand("margaret.thatcher@gov.uk", "The Good times  ");
        controller.runCommand(cmd);
        GovernmentRepresentative govRep = (GovernmentRepresentative) cmd.getResult();

        assertEquals(true, govRep.checkPasswordMatch("The Good times  "));
        assertEquals("margaret.thatcher@gov.uk", govRep.getEmail());

        controller.runCommand(new LogoutCommand());
    }

}
