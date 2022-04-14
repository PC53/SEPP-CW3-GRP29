import command.*;
import controller.Context;
import controller.Controller;
import logging.Logger;
import model.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import javax.naming.ldap.Control;
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
    // create performances, get list of events based on consumer preferences, view events (ticket availability,
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

    @Test
    void getListofEvents() {
        Controller controller = new Controller();
    }
}
