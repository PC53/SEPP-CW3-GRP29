import command.*;
import controller.Controller;
import logging.Logger;
import model.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CancelBookingTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void register3Consumers(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "papu perpendicular",
                "hameshaPerpendicular@wasseyMail.co.uk",
                "077893153480",
                "TangentKoMaarnaHain",
                "faisal@hotmail.co.uk"
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterConsumerCommand(
                "Jane Giantsdottir",
                "jane@inf.ed.ac.uk",
                "04462187232",
                "giantsRverycool",
                "jane@aol.com"
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterConsumerCommand(
                "Wednesday Kebede",
                "i-will-kick-your@gmail.com",
                "-",
                "it is wednesday my dudes",
                "i-will-kick-your@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static long bookCinemaEventPerformances(Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Cinema Conglomerate",
                "Global Office, International Space Station",
                "$$$@there'sNoEmailValidation.wahey!",
                "Mrs Representative",
                "odeon@cineworld.com",
                "F!ghT th3 R@Pture",
                List.of("Dr Strangelove"),
                List.of("we_dont_get_involved@cineworld.com")
        ));

        CreateTicketedEventCommand eventCmd1 = new CreateTicketedEventCommand(
                "The LEGO Movie",
                EventType.Movie,
                50,
                15.75,
                false
        );
        controller.runCommand(eventCmd1);
        long eventNumber1 = eventCmd1.getResult();
        controller.runCommand(new AddEventPerformanceCommand(
                eventNumber1,
                "You know how much it hurts when you step on a Lego piece?!?!",
                LocalDateTime.now().minusWeeks(1),
                LocalDateTime.now().minusWeeks(1).plusHours(2),
                Collections.emptyList(),
                false,
                true,
                false,
                50,
                50
        ));

        AddEventPerformanceCommand socialDistancedPerformancecmd = new AddEventPerformanceCommand(
                eventNumber1,
                "some fancy mall's 50th hall",
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(2),
                Collections.emptyList(),
                true,
                true,
                false,
                25,
                50
        );
        controller.runCommand(socialDistancedPerformancecmd);
        long performance2Number = socialDistancedPerformancecmd.getResult().getPerformanceNumber();

        controller.runCommand(new LogoutCommand());

        // Book Event Performance
        controller.runCommand(new LoginCommand("hameshaPerpendicular@wasseyMail.co.uk",
                "TangentKoMaarnaHain"));

        BookEventCommand bookEventCommand = new BookEventCommand(eventNumber1,performance2Number,15);
        controller.runCommand(bookEventCommand);

        return bookEventCommand.getResult();
    }

    private static boolean cancelBooking(Controller controller , long bookingNumber){
        CancelBookingCommand cancelBookingCommand = new CancelBookingCommand(bookingNumber);
        controller.runCommand(cancelBookingCommand);
        return cancelBookingCommand.getResult();
    }

    @Test
    void cancelBookingTests(){
        Controller controller = new Controller();

        register3Consumers(controller);
        long bookingNumber = bookCinemaEventPerformances(controller);

        assertTrue(cancelBooking(controller,bookingNumber));


    }

}
