import command.*;
import controller.Controller;
import logging.Logger;
import model.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookEventTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void register2Consumers(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Julius Temitope",
                "juliustemitope17@gmail.com",
                "07438462934",
                "abcdef",
                "juliustemitope7@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterConsumerCommand(
                "Helladios Carman",
                "helladioscarman35@ed.ac.uk",
                "07430264816",
                "carmen123",
                "helladioscarman731@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static void registerEntProvider(Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Jackson Incorporated",
                "EH8 123",
                "jackson123@gmail.com",
                "Jackson Mike",
                "jacksonmike@gmail.com",
                "abcedefg",
                new ArrayList<String>(),
                new ArrayList<String>()
        ));
    }

    private static long createTicketedEvent(Controller controller) {
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Event One",
                EventType.Movie,
                2,
                100,
                false
        );
        controller.runCommand(eventCmd);
        return eventCmd.getResult();
    }

    private static long createPerformance(Controller controller, long eventNumber) {
        ArrayList<String> performers = new ArrayList<String>();
        performers.add("Performer A");
        performers.add("Performer B");
        AddEventPerformanceCommand performanceCmd = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(6),
                performers,
                true,
                true,
                true,
                120,
                1200
        );
        controller.runCommand(performanceCmd);
        return performanceCmd.getResult().getPerformanceNumber();
    }

    private static void logOutCommand(Controller controller) {
        controller.runCommand(new LogoutCommand());
    }

    private static void consumer1LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("juliustemitope17@gmail.com", "abcdef"));
    }

    private static void consumer2LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("helladioscarman35@ed.ac.uk", "carmen123"));
    }

    private static Long consumer1BookingTicketsWithFalseEventNumber(Controller controller, long performanceNumber) {
        long eventNumber = 9999;
        BookEventCommand bookEventCommand = new BookEventCommand(eventNumber, performanceNumber, 1);
        controller.runCommand(bookEventCommand);
        return bookEventCommand.getResult();
    }

    private static Long consumer2BookingTicketsWithFalsePerformanceNumber(Controller controller, long eventNumber) {
        long performanceNumber = 9999;
        BookEventCommand bookEventCommand = new BookEventCommand(eventNumber, performanceNumber, 1);
        controller.runCommand(bookEventCommand);
        return bookEventCommand.getResult();
    }

    private static long consumer1BookingOneTicket(Controller controller, long eventNumber, long performanceNumber) {
        BookEventCommand bookEventCommand = new BookEventCommand(eventNumber, performanceNumber, 1);
        controller.runCommand(bookEventCommand);
        return bookEventCommand.getResult();
    }

    private static Long consumer2BookingThreeTicket(Controller controller, long eventNumber, long performanceNumber) {
        BookEventCommand bookEventCommand = new BookEventCommand(eventNumber, performanceNumber, 3);
        controller.runCommand(bookEventCommand);
        return bookEventCommand.getResult();
    }

    @Test
    void bookingTicketsWithFalseNumbersTests(){
        Controller controller = new Controller();
        register2Consumers(controller);
        registerEntProvider(controller);
        long eventNumber = createTicketedEvent(controller);
        long performanceNumber = createPerformance(controller, eventNumber);
        logOutCommand(controller);
        consumer1LogIn(controller);
        Long bookingNumber1 = consumer1BookingTicketsWithFalseEventNumber(controller, performanceNumber);
        logOutCommand(controller);
        consumer2LogIn(controller);
        Long bookingNumber2 = consumer2BookingTicketsWithFalsePerformanceNumber(controller, eventNumber);
        assertNull(bookingNumber1);
        assertNull(bookingNumber2);
    }

    @Test
    void bookingSufficientTicketsTests(){
        Controller controller = new Controller();
        register2Consumers(controller);
        registerEntProvider(controller);
        long eventNumber = createTicketedEvent(controller);
        long performanceNumber = createPerformance(controller, eventNumber);
        logOutCommand(controller);
        consumer1LogIn(controller);
        long bookingNumber = consumer1BookingOneTicket(controller, eventNumber, performanceNumber);
        assertEquals(1, bookingNumber);
    }

    @Test
    void bookingInsufficientTicketsTests(){
        Controller controller = new Controller();
        register2Consumers(controller);
        registerEntProvider(controller);
        long eventNumber = createTicketedEvent(controller);
        long performanceNumber = createPerformance(controller, eventNumber);
        logOutCommand(controller);
        consumer2LogIn(controller);
        Long bookingNumber = consumer2BookingThreeTicket(controller, eventNumber, performanceNumber);
        assertNull(bookingNumber);
    }
}
