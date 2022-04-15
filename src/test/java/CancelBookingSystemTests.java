import command.*;
import controller.Context;
import controller.Controller;
import logging.Logger;
import model.Consumer;
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

public class CancelBookingSystemTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void loginGovernmentRepresentative(Controller controller) {
        controller.runCommand(new LoginCommand("margaret.thatcher@gov.uk", "The Good times  "));
    }

    private static void loginSportsProvider(Controller controller) {
        controller.runCommand(new LoginCommand("thirdpartyhandler@gmail","F!ghT th3 R@Pture"));
    }

    private static void loginCinemaProvider(Controller controller) {
        controller.runCommand(new LoginCommand("odeon@cineworld.com","F!ghT th3 R@Pture"));
    }

    private static void loginConsumer1(Controller controller) {
        controller.runCommand(new LoginCommand("hameshaPerpendicular@wasseyMail.co.uk", "TangentKoMaarnaHain"));
    }

    private static void loginConsumer2(Controller controller) {
        controller.runCommand(new LoginCommand("jane@inf.ed.ac.uk", "giantsRverycool"));
    }

    private static void registerConsumers(Controller controller) {
        RegisterConsumerCommand regCmd = new RegisterConsumerCommand(
                "papu perpendicular",
                "hameshaPerpendicular@wasseyMail.co.uk",
                "077893153480",
                "TangentKoMaarnaHain",
                "faisal@hotmail.co.uk");
        controller.runCommand(regCmd);
        controller.runCommand(new LogoutCommand());

        RegisterConsumerCommand regCmd2 = new RegisterConsumerCommand(
                "Jane Giantsdottir",
                "jane@inf.ed.ac.uk",
                "04462187232",
                "giantsRverycool",
                "jane@aol.com");
        controller.runCommand(regCmd2);
        controller.runCommand(new LogoutCommand());
    }

    private static long createSportsEvent(Controller controller){
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

        return ticketCmd.getResult();
    }

    private static long addSportEventPerformance1(Controller controller,long eventNumber){
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
        return perf1Cmd.getResult().getPerformanceNumber();
    }

    private static long addSportEventPerformance2(Controller controller,long eventNumber){
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
        return perf2Cmd.getResult().getPerformanceNumber();
    }

    private static long createCinemaEvent(Controller controller){
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
        return eventCmd1.getResult();
    }

    private static long addCinemaPerformanceSocialDist(Controller controller,long eventNumber){
        AddEventPerformanceCommand perf2Cmd = new AddEventPerformanceCommand(
                eventNumber,
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
        controller.runCommand(perf2Cmd);
        return perf2Cmd.getResult().getPerformanceNumber();
    }

    @Test
    void cancelBookingRebookSeats() {
        Controller controller = new Controller();
        registerConsumers(controller);

        long eventNumber = createCinemaEvent(controller);
        long perfNumDistancing = addCinemaPerformanceSocialDist(controller,eventNumber);

        controller.runCommand(new LogoutCommand());

        loginConsumer1(controller);

        BookEventCommand cmd1 = new BookEventCommand(eventNumber,perfNumDistancing,15);
        controller.runCommand(cmd1);

        cancelBooking(controller, cmd1.getResult());

        loginConsumer2(controller);
        BookEventCommand cmd2 = new BookEventCommand(eventNumber,perfNumDistancing,10);
        controller.runCommand(cmd2);

        BookEventCommand cmd3 = new BookEventCommand(eventNumber,perfNumDistancing,10);
        controller.runCommand(cmd3);

        assertNotNull(cmd3.getResult());
    }

    private static boolean cancelBooking(Controller controller , long bookingNumber){
        CancelBookingCommand cancelBookingCommand = new CancelBookingCommand(bookingNumber);
        controller.runCommand(cancelBookingCommand);
        return cancelBookingCommand.getResult();
    }

    @Test
    void otherUserTriesToCancel(){
        Controller controller = new Controller();
        registerConsumers(controller);

        long eventNumber = createCinemaEvent(controller);
        long perfNumDistancing = addCinemaPerformanceSocialDist(controller,eventNumber);

        loginConsumer1(controller);

        BookEventCommand cmd1 = new BookEventCommand(eventNumber,perfNumDistancing,15);
        controller.runCommand(cmd1);

        loginConsumer2(controller);

        assertFalse(cancelBooking(controller, cmd1.getResult()));

    }

    // invalid booking number
    @Test
    void invalidBookingNumber(){
        Controller controller = new Controller();
        registerConsumers(controller);

        long eventNumber = createCinemaEvent(controller);
        long perfNumDistancing = addCinemaPerformanceSocialDist(controller,eventNumber);

        loginConsumer1(controller);

        BookEventCommand cmd1 = new BookEventCommand(eventNumber,perfNumDistancing,15);
        controller.runCommand(cmd1);

        assertFalse(cancelBooking(controller,1234556789));
    }


    // cancel booking for cancelled event
    @Test
    void cancelForCancelledEvent(){
        Controller controller = new Controller();
        registerConsumers(controller);

        long eventNumber = createSportsEvent(controller);
        long perf1Number = addSportEventPerformance1(controller, eventNumber);
        long perf2Number = addSportEventPerformance2(controller, eventNumber);

        loginConsumer1(controller);

        BookEventCommand bookCSK = new BookEventCommand(eventNumber,perf1Number,10);
        controller.runCommand(bookCSK);

        loginConsumer2(controller);
        BookEventCommand bookRCB = new BookEventCommand(eventNumber,perf2Number,10);
        controller.runCommand(bookRCB);
        Long rcbNumber = bookRCB.getResult();

        loginSportsProvider(controller);

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
    void cancelBookingAfterPerformance() throws InterruptedException {
        Controller controller = new Controller();

        registerConsumers(controller);
        long eventNumber = createCinemaEvent(controller);
        AddEventPerformanceCommand perf1Cmd = new AddEventPerformanceCommand(
                eventNumber,
                "You know how much it hurts when you step on a Lego piece?!?!",
                LocalDateTime.now().plusSeconds(5),
                LocalDateTime.now().plusSeconds(10),
                Collections.emptyList(),
                false,
                true,
                false,
                50,
                50
        );
        controller.runCommand(perf1Cmd);
        long performanceNumber = perf1Cmd.getResult().getPerformanceNumber();

        loginConsumer1(controller);
        BookEventCommand booking = new BookEventCommand(eventNumber,performanceNumber,10);
        controller.runCommand(booking);

        java.util.concurrent.TimeUnit.SECONDS.sleep(10);

        assertFalse(cancelBooking(controller, booking.getResult()));

    }

}
