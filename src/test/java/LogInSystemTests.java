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
                "adamapple7@hotmail.co.uk",
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

    // Large test to test the logging in of different consumer's with different details.
    // also testing the logging in of consumers after their details change.
    // This may need to be split into smaller tests.
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

    // -----------TESTING ENTERTAINMENT REP LOGIN-----------


}
