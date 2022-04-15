import command.*;
import controller.Context;
import controller.Controller;
import logging.Logger;
import model.EntertainmentProvider;
import model.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    private static Long bookCinemaEventPerformances(Controller controller) {
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

        BookEventCommand cmd1 = new BookEventCommand(eventNumber1,performance2Number,15);
        controller.runCommand(cmd1);

        cancelBooking(controller, cmd1.getResult());

        BookEventCommand cmd2 = new BookEventCommand(eventNumber1,performance2Number,10);
        controller.runCommand(cmd2);

        BookEventCommand cmd3 = new BookEventCommand(eventNumber1,performance2Number,10);
        controller.runCommand(cmd3);

        return cmd3.getResult();
    }

    private static boolean cancelBooking(Controller controller , long bookingNumber){
        CancelBookingCommand cancelBookingCommand = new CancelBookingCommand(bookingNumber);
        controller.runCommand(cancelBookingCommand);
        return cancelBookingCommand.getResult();
    }

    // other
    private static boolean otherUserTriesToCancel(Controller controller, long bookingNumber){
        LoginCommand newLogin = new LoginCommand("i-will-kick-your@gmail.com","it is wednesday my dudes");
        controller.runCommand(newLogin);

        return cancelBooking(controller, bookingNumber);

    }

    // invalid booking number
    private static boolean invalidBookingNumber(Controller controller){
        return cancelBooking(controller,1234556789);
    }


    // cancel booking for cancelled event
    @Test
    void cancelForCancelledEvent(){
        Controller controller = new Controller();
        register3Consumers(controller);

        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "BCCI",
                "some glass building",
                "lalitmodi@$$$",
                "Lalit modi",
                "thirdpartyhandler@gmail",
                "F!ghT th3 R@Pture",
                List.of("sg"),
                List.of("dont ask us@cricket")
        ));

        CreateTicketedEventCommand ticketCmd = new CreateTicketedEventCommand(
                "IPl",
                EventType.Sports,
                1000,
                100,
                true
        );
        controller.runCommand(ticketCmd);

        Long eventNumber = ticketCmd.getResult();

        AddEventPerformanceCommand perf1Cmd = new AddEventPerformanceCommand(
                eventNumber,
                "wankhede",
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(5).plusHours(2),
                Collections.singletonList("csk"),
                false,
                true,
                true,
                100,
                500
        );
        controller.runCommand(perf1Cmd);
        long perf1Number = perf1Cmd.getResult().getPerformanceNumber();

        AddEventPerformanceCommand perf2Cmd = new AddEventPerformanceCommand(
                eventNumber,
                "bangalore",
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(3).plusHours(2),
                Collections.singletonList("rcb"),
                false,
                true,
                true,
                100,
                500
        );
        controller.runCommand(perf2Cmd);
        long perf2Number = perf2Cmd.getResult().getPerformanceNumber();

        // login consumer
        controller.runCommand(new LoginCommand("jane@inf.ed.ac.uk","giantsRverycool"));

        // book performances
        BookEventCommand bookCSK = new BookEventCommand(eventNumber,perf1Number,10);
        controller.runCommand(bookCSK);
        Long cskNumber = bookCSK.getResult();

        BookEventCommand bookRCB = new BookEventCommand(eventNumber,perf2Number,10);
        controller.runCommand(bookRCB);
        Long rcbNumber = bookRCB.getResult();

        // login Entertainment provider
        controller.runCommand(new LoginCommand("thirdpartyhandler@gmail","F!ghT th3 R@Pture"));

        // Cancel the event
        CancelEventCommand cancelIPL = new CancelEventCommand(eventNumber,"LOL get COVID");
        controller.runCommand(cancelIPL);

        // login with consumer
        controller.runCommand(new LoginCommand("jane@inf.ed.ac.uk","giantsRverycool"));

        // now try to cancel booking
        CancelBookingCommand cancelRCB = new CancelBookingCommand(rcbNumber);
        controller.runCommand(cancelRCB);

        assertFalse(cancelRCB.getResult());
    }

    // cancel booking for event after end date time

    @Test
    void cancelBookingTests(){
        Controller controller = new Controller();

        register3Consumers(controller);
        Long bookingNumber = bookCinemaEventPerformances(controller);

        assert(bookingNumber != null);

        assertFalse(otherUserTriesToCancel(controller, bookingNumber));

        assertFalse(invalidBookingNumber(controller));

    }

}
