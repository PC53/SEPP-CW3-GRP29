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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterConsumerSystemTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void registerConsumer1(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Julius Temitope",
                "juliustemitope17@gmail.com",
                "07438462934",
                "abcdef",
                "juliustemitope7@gmail.com"
        ));
    }

    private static void registerConsumer2(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Helladios Carman",
                "helladioscarman35@ed.ac.uk",
                "07430264816",
                "carmen123",
                "helladioscarman731@gmail.com"
        ));
    }

    // Inserted into tests below, not necessary
    private static void registerConsumer3(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Moyra Clarke",
                "moyraclarke177@gmail.com",
                "-",
                "12345678",
                "moyraclarke177@gmail.com"
        ));
    }

    // Inserted into tests below, not necessary
    private static void updateConsumer1Details(Controller controller) {
        controller.runCommand(new UpdateConsumerProfileCommand(
                "abcdef",
                "julius36@gmail.com",
                "Temitope Julius",
                "07437461974",
                "fedcba",
                "julius36@gmail.com",
                new ConsumerPreferences()
        ));
    }

    // Inserted into tests below, not necessary
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

    @Test
    void RegisterConsumer1() {
        Controller controller = new Controller();

        RegisterConsumerCommand cmd = new RegisterConsumerCommand(
                "Julius Temitope",
                "juliustemitope17@gmail.com",
                "07438462934",
                "abcdef",
                "juliustemitope7@gmail.com"
        );

        controller.runCommand(cmd);
        Consumer consumer1 = cmd.getResult();
        controller.runCommand(new LogoutCommand());

        assertEquals("Julius Temitope", consumer1.getName());
        assertEquals("juliustemitope17@gmail.com", consumer1.getEmail());
        assertEquals("juliustemitope7@gmail.com", consumer1.getPaymentAccountEmail());
        assertEquals(true, consumer1.checkPasswordMatch("abcdef"));
    }

    @Test
    void RegisterConsumer2() {
        Controller controller = new Controller();

        RegisterConsumerCommand cmd = new RegisterConsumerCommand(
                "Helladios Carman",
                "helladioscarman35@ed.ac.uk",
                "07430264816",
                "carmen123",
                "helladioscarman731@gmail.com"
        );

        controller.runCommand(cmd);
        Consumer consumer2 = cmd.getResult();
        controller.runCommand(new LogoutCommand());

        assertEquals("Helladios Carman", consumer2.getName());
        assertEquals("helladioscarman35@ed.ac.uk", consumer2.getEmail());
        assertEquals("helladioscarman731@gmail.com", consumer2.getPaymentAccountEmail());
        assertEquals(true, consumer2.checkPasswordMatch("carmen123"));
    }

    @Test
    void RegisterConsumer3() {
        Controller controller = new Controller();

        RegisterConsumerCommand cmd = new RegisterConsumerCommand(
                "Moyra Clarke",
                "moyraclarke177@gmail.com",
                "-",
                "12345678",
                "moyraclarke177@gmail.com"
        );

        controller.runCommand(cmd);
        Consumer consumer3 = cmd.getResult();
        controller.runCommand(new LogoutCommand());

        assertEquals("Moyra Clarke", consumer3.getName());
        assertEquals("moyraclarke177@gmail.com", consumer3.getEmail());
        assertEquals("moyraclarke177@gmail.com", consumer3.getPaymentAccountEmail());
        assertEquals(true, consumer3.checkPasswordMatch("12345678"));
    }

    @Test
    void UpdateConsumer1Details() {
        Controller controller = new Controller();

        registerConsumer1(controller);

        UpdateConsumerProfileCommand cmd = new UpdateConsumerProfileCommand(
                "abcdef",
                "julius36@gmail.com",
                "Temitope Julius",
                "07437461974",
                "fedcba",
                "julius36@gmail.com",
                new ConsumerPreferences()
        );

        controller.runCommand(cmd);
        Boolean successfulUpdate = (Boolean) cmd.getResult();
        controller.runCommand(new LogoutCommand());

        assertEquals(true, successfulUpdate);
    }


    @Test
    void UpdateConsumer2Preferences() {
        Controller controller = new Controller();

        registerConsumer2(controller);

        ConsumerPreferences consumer2Preferences = new ConsumerPreferences();
        consumer2Preferences.preferSocialDistancing = true;
        consumer2Preferences.preferOutdoorsOnly = false;
        consumer2Preferences.preferAirFiltration = true;
        consumer2Preferences.preferredMaxCapacity = 100;
        consumer2Preferences.preferredMaxVenueSize = 1000;
        UpdateConsumerProfileCommand cmd = new UpdateConsumerProfileCommand(
                "carmen123",
                "helladioscarman35@ed.ac.uk",
                "Helladios Carman",
                "07430264816",
                "carmen123",
                "helladioscarman731@gmail.com",
                consumer2Preferences
        );

        controller.runCommand(cmd);
        Boolean successfulUpdate = (Boolean) cmd.getResult();
        controller.runCommand(new LogoutCommand());

        assertEquals(true, successfulUpdate);
    }

    @Test
    void checkConsumer3InitialPreferences() {
        Controller controller = new Controller();

        ConsumerPreferences consumer3Preferences = new ConsumerPreferences();
        consumer3Preferences.preferSocialDistancing = false;
        consumer3Preferences.preferOutdoorsOnly = false;
        consumer3Preferences.preferAirFiltration = false;
        consumer3Preferences.preferredMaxCapacity = Integer.MAX_VALUE;
        consumer3Preferences.preferredMaxVenueSize = Integer.MAX_VALUE;
        RegisterConsumerCommand cmd = new RegisterConsumerCommand(
                "Moyra Clarke",
                "moyraclarke177@gmail.com",
                "-",
                "12345678",
                "moyraclarke177@gmail.com"
        );

        controller.runCommand(cmd);
        Consumer consumer3 = cmd.getResult();
        controller.runCommand(new LogoutCommand());

        assertEquals(consumer3Preferences.toString(), consumer3.getPreferences().toString());
    }
}
