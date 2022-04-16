import command.*;
import controller.Controller;
import logging.Logger;
import model.Booking;
import model.EventType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestBookingRecordsSystemTests {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static void registerConsumer(Controller controller) {
        controller.runCommand(new RegisterConsumerCommand(
                "Julius Temitope",
                "juliustemitope17@gmail.com",
                "07438462934",
                "abcdef",
                "juliustemitope7@gmail.com"
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static void register2EntProvider(Controller controller) {
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Jackson Incorporated",
                "EH8 123",
                "jackson123@gmail.com",
                "Jackson Mike",
                "jacksonmike@gmail.com",
                "abcdefgh",
                new ArrayList<String>(),
                new ArrayList<String>()
        ));
        controller.runCommand(new LogoutCommand());
        controller.runCommand(new RegisterEntertainmentProviderCommand(
                "Peter Incorporated",
                "EH8 234",
                "peter234@gmail.com",
                "Peter Yao",
                "peteryao@gmail.com",
                "abcd1234",
                new ArrayList<String>(),
                new ArrayList<String>()
        ));
        controller.runCommand(new LogoutCommand());
    }

    private static long createTicketedEvent1(Controller controller) {
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Event One",
                EventType.Movie,
                50,
                100,
                false
        );
        controller.runCommand(eventCmd);
        return eventCmd.getResult();
    }

    private static long createPerformance1(Controller controller, long eventNumber) {
        ArrayList<String> performers = new ArrayList<String>();
        performers.add("Performer A");
        performers.add("Performer B");
        AddEventPerformanceCommand performanceCmd = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(6),
                performers,
                false,
                false,
                false,
                120,
                1200
        );
        controller.runCommand(performanceCmd);
        return performanceCmd.getResult().getPerformanceNumber();
    }

    private static long createTicketedEvent2(Controller controller) {
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Event Two",
                EventType.Sports,
                50,
                100,
                true
        );
        controller.runCommand(eventCmd);
        return eventCmd.getResult();
    }

    private static long createPerformance2(Controller controller, long eventNumber) {
        ArrayList<String> performers = new ArrayList<String>();
        performers.add("Performer A");
        performers.add("Performer B");
        AddEventPerformanceCommand performanceCmd = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusMonths(2),
                LocalDateTime.now().plusMonths(2).plusHours(6),
                performers,
                false,
                false,
                false,
                120,
                1200
        );
        controller.runCommand(performanceCmd);
        return performanceCmd.getResult().getPerformanceNumber();
    }

    private static long createTicketedEvent3(Controller controller) {
        CreateTicketedEventCommand eventCmd = new CreateTicketedEventCommand(
                "Event Three",
                EventType.Sports,
                50,
                100,
                true
        );
        controller.runCommand(eventCmd);
        return eventCmd.getResult();
    }

    private static long createPerformance3(Controller controller, long eventNumber) {
        ArrayList<String> performers = new ArrayList<String>();
        performers.add("Performer A");
        performers.add("Performer B");
        AddEventPerformanceCommand performanceCmd = new AddEventPerformanceCommand(
                eventNumber,
                "EH8 456",
                LocalDateTime.now().plusWeeks(2),
                LocalDateTime.now().plusWeeks(2).plusHours(6),
                performers,
                false,
                false,
                false,
                120,
                1200
        );
        controller.runCommand(performanceCmd);
        return performanceCmd.getResult().getPerformanceNumber();
    }

    private static void logOutCommand(Controller controller) {
        controller.runCommand(new LogoutCommand());
    }

    private static void consumerLogIn(Controller controller) {
        controller.runCommand(new LoginCommand("juliustemitope17@gmail.com", "abcdef"));
    }

    private static void EP1LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("jacksonmike@gmail.com", "abcdefgh"));
    }

    private static void EP2LogIn(Controller controller) {
        controller.runCommand(new LoginCommand("peteryao@gmail.com", "abcd1234"));
    }

    private static void govRepLogIn(Controller controller) {
        controller.runCommand(new LoginCommand("margaret.thatcher@gov.uk", "The Good times  "));
    }

    private static long consumerBookingOneTicket(Controller controller, long eventNumber, long performanceNumber) {
        BookEventCommand bookEventCommand = new BookEventCommand(eventNumber, performanceNumber, 1);
        controller.runCommand(bookEventCommand);
        return bookEventCommand.getResult();
    }

    @Test
    void requestBookingRecordsStandardTests() {
        Controller controller = new Controller();

        // Create users in the system
        registerConsumer(controller);
        register2EntProvider(controller);

        // Log in with Entertainment Provider 1 and create two events with one performance for each
        EP1LogIn(controller);
        long eventNumber1 = createTicketedEvent1(controller);
        long performanceNumber1 = createPerformance1(controller,eventNumber1);
        long eventNumber2 = createTicketedEvent2(controller);
        long performanceNumber2 = createPerformance2(controller,eventNumber2);
        logOutCommand(controller);

        // Log in with Entertainment Provider 2 and create one event with one performance
        EP2LogIn(controller);
        long eventNumber3 = createTicketedEvent3(controller);
        long performanceNumber3 = createPerformance3(controller,eventNumber3);
        logOutCommand(controller);

        // Log in with government representative account and accept the sponsorship request
        govRepLogIn(controller);
        controller.runCommand(new RespondSponsorshipCommand(1,10));
        controller.runCommand(new RespondSponsorshipCommand(2,10));
        logOutCommand(controller);

        // Log in with consumer account and make bookings for each event
        consumerLogIn(controller);
        long bookingNumber1 = consumerBookingOneTicket(controller,eventNumber1,performanceNumber1);
        long bookingNumber2 = consumerBookingOneTicket(controller,eventNumber2,performanceNumber2);
        long bookingNumber3 = consumerBookingOneTicket(controller,eventNumber3,performanceNumber3);
        logOutCommand(controller);

        // Log in with government representative account and request the booking records
        govRepLogIn(controller);
        GovernmentReport1Command cmd = new GovernmentReport1Command(LocalDateTime.now(), LocalDateTime.now().plusMonths(3));
        controller.runCommand(cmd);
        List<Booking> bookings = cmd.getResult();

        assertEquals(2, bookings.size());
        assertEquals(bookingNumber2, bookings.get(0).getBookingNumber());
        assertEquals("juliustemitope17@gmail.com", bookings.get(0).getBooker().getEmail());
        assertEquals(eventNumber2, bookings.get(0).getEventPerformance().getEvent().getEventNumber());
        assertEquals(performanceNumber2, bookings.get(0).getEventPerformance().getPerformanceNumber());
        assertEquals(bookingNumber3, bookings.get(1).getBookingNumber());
        assertEquals("juliustemitope17@gmail.com", bookings.get(1).getBooker().getEmail());
        assertEquals(eventNumber3, bookings.get(1).getEventPerformance().getEvent().getEventNumber());
        assertEquals(performanceNumber3, bookings.get(1).getEventPerformance().getPerformanceNumber());
    }

    @Test
    void requestBookingRecordsWithNoGovRepAccountTests() {
        Controller controller = new Controller();

        // Create users in the system
        registerConsumer(controller);
        register2EntProvider(controller);

        // Log in with Entertainment Provider 1 and create two events with one performance for each
        EP1LogIn(controller);
        long eventNumber1 = createTicketedEvent1(controller);
        long performanceNumber1 = createPerformance1(controller,eventNumber1);
        long eventNumber2 = createTicketedEvent2(controller);
        long performanceNumber2 = createPerformance2(controller,eventNumber2);
        logOutCommand(controller);

        // Log in with Entertainment Provider 2 and create one event with one performance
        EP2LogIn(controller);
        long eventNumber3 = createTicketedEvent3(controller);
        long performanceNumber3 = createPerformance3(controller,eventNumber3);
        logOutCommand(controller);

        // Log in with government representative account and accept the sponsorship request
        govRepLogIn(controller);
        controller.runCommand(new RespondSponsorshipCommand(1,10));
        controller.runCommand(new RespondSponsorshipCommand(2,10));
        logOutCommand(controller);

        // Log in with consumer account and make bookings for each event
        consumerLogIn(controller);
        long bookingNumber1 = consumerBookingOneTicket(controller,eventNumber1,performanceNumber1);
        long bookingNumber2 = consumerBookingOneTicket(controller,eventNumber2,performanceNumber2);
        long bookingNumber3 = consumerBookingOneTicket(controller,eventNumber3,performanceNumber3);
        logOutCommand(controller);

        // Log in with entertainment provider 1 account and request the booking records
        EP1LogIn(controller);
        GovernmentReport1Command cmd1 = new GovernmentReport1Command(LocalDateTime.now(), LocalDateTime.now().plusMonths(3));
        controller.runCommand(cmd1);
        List<Booking> bookings1 = cmd1.getResult();
        assertTrue(bookings1.isEmpty()); // Expect empty result
        logOutCommand(controller);

        // Log in with entertainment provider 2 account and request the booking records
        EP2LogIn(controller);
        GovernmentReport1Command cmd2 = new GovernmentReport1Command(LocalDateTime.now(), LocalDateTime.now().plusMonths(3));
        controller.runCommand(cmd2);
        List<Booking> bookings2 = cmd2.getResult();
        assertTrue(bookings2.isEmpty()); // Expect empty result
        logOutCommand(controller);

        // Log in with consumer account and request the booking records
        consumerLogIn(controller);
        GovernmentReport1Command cmd3 = new GovernmentReport1Command(LocalDateTime.now(), LocalDateTime.now().plusMonths(3));
        controller.runCommand(cmd3);
        List<Booking> bookings3 = cmd3.getResult();
        assertTrue(bookings3.isEmpty()); // Expect empty result
        logOutCommand(controller);
    }

    @Test
    void requestBookingRecordsWithRejectedSponsorshipRequestsTests() {
        Controller controller = new Controller();

        // Create users in the system
        registerConsumer(controller);
        register2EntProvider(controller);

        // Log in with Entertainment Provider 1 and create two events with one performance for each
        EP1LogIn(controller);
        long eventNumber1 = createTicketedEvent1(controller);
        long performanceNumber1 = createPerformance1(controller,eventNumber1);
        long eventNumber2 = createTicketedEvent2(controller);
        long performanceNumber2 = createPerformance2(controller,eventNumber2);
        logOutCommand(controller);

        // Log in with Entertainment Provider 2 and create one event with one performance
        EP2LogIn(controller);
        long eventNumber3 = createTicketedEvent3(controller);
        long performanceNumber3 = createPerformance3(controller,eventNumber3);
        logOutCommand(controller);

        // Log in with government representative account and accept the sponsorship request
        govRepLogIn(controller);
        controller.runCommand(new RespondSponsorshipCommand(1,10));
        controller.runCommand(new RespondSponsorshipCommand(2,0));
        logOutCommand(controller);

        // Log in with consumer account and make bookings for each event
        consumerLogIn(controller);
        long bookingNumber1 = consumerBookingOneTicket(controller,eventNumber1,performanceNumber1);
        long bookingNumber2 = consumerBookingOneTicket(controller,eventNumber2,performanceNumber2);
        long bookingNumber3 = consumerBookingOneTicket(controller,eventNumber3,performanceNumber3);
        logOutCommand(controller);

        // Log in with government representative account and request the booking records
        govRepLogIn(controller);
        GovernmentReport1Command cmd = new GovernmentReport1Command(LocalDateTime.now(), LocalDateTime.now().plusMonths(3));
        controller.runCommand(cmd);
        List<Booking> bookings = cmd.getResult();

        assertEquals(1, bookings.size());
        assertEquals(bookingNumber2, bookings.get(0).getBookingNumber());
        assertEquals("juliustemitope17@gmail.com", bookings.get(0).getBooker().getEmail());
        assertEquals(eventNumber2, bookings.get(0).getEventPerformance().getEvent().getEventNumber());
        assertEquals(performanceNumber2, bookings.get(0).getEventPerformance().getPerformanceNumber());
    }

    @Test
    void requestBookingRecordsWithSmallerTimeRangeTests() {
        Controller controller = new Controller();

        // Create users in the system
        registerConsumer(controller);
        register2EntProvider(controller);

        // Log in with Entertainment Provider 1 and create two events with one performance for each
        EP1LogIn(controller);
        long eventNumber1 = createTicketedEvent1(controller);
        long performanceNumber1 = createPerformance1(controller,eventNumber1);
        long eventNumber2 = createTicketedEvent2(controller);
        long performanceNumber2 = createPerformance2(controller,eventNumber2);
        logOutCommand(controller);

        // Log in with Entertainment Provider 2 and create one event with one performance
        EP2LogIn(controller);
        long eventNumber3 = createTicketedEvent3(controller);
        long performanceNumber3 = createPerformance3(controller,eventNumber3);
        logOutCommand(controller);

        // Log in with government representative account and accept the sponsorship request
        govRepLogIn(controller);
        controller.runCommand(new RespondSponsorshipCommand(1,10));
        controller.runCommand(new RespondSponsorshipCommand(2,10));
        logOutCommand(controller);

        // Log in with consumer account and make bookings for each event
        consumerLogIn(controller);
        long bookingNumber1 = consumerBookingOneTicket(controller,eventNumber1,performanceNumber1);
        long bookingNumber2 = consumerBookingOneTicket(controller,eventNumber2,performanceNumber2);
        long bookingNumber3 = consumerBookingOneTicket(controller,eventNumber3,performanceNumber3);
        logOutCommand(controller);

        // Log in with government representative account and request the booking records
        govRepLogIn(controller);
        GovernmentReport1Command cmd = new GovernmentReport1Command(LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(3));
        controller.runCommand(cmd);
        List<Booking> bookings = cmd.getResult();

        assertEquals(1, bookings.size());
        assertEquals(bookingNumber2, bookings.get(0).getBookingNumber());
        assertEquals("juliustemitope17@gmail.com", bookings.get(0).getBooker().getEmail());
        assertEquals(eventNumber2, bookings.get(0).getEventPerformance().getEvent().getEventNumber());
        assertEquals(performanceNumber2, bookings.get(0).getEventPerformance().getPerformanceNumber());
    }

    @Test
    void requestBookingRecordsWithCancelledEventsTests() {
        Controller controller = new Controller();

        // Create users in the system
        registerConsumer(controller);
        register2EntProvider(controller);

        // Log in with Entertainment Provider 1 and create two events with one performance for each
        EP1LogIn(controller);
        long eventNumber1 = createTicketedEvent1(controller);
        long performanceNumber1 = createPerformance1(controller,eventNumber1);
        long eventNumber2 = createTicketedEvent2(controller);
        long performanceNumber2 = createPerformance2(controller,eventNumber2);
        logOutCommand(controller);

        // Log in with Entertainment Provider 2 and create one event with one performance
        EP2LogIn(controller);
        long eventNumber3 = createTicketedEvent3(controller);
        long performanceNumber3 = createPerformance3(controller,eventNumber3);
        logOutCommand(controller);

        // Log in with government representative account and accept the sponsorship request
        govRepLogIn(controller);
        controller.runCommand(new RespondSponsorshipCommand(1,10));
        controller.runCommand(new RespondSponsorshipCommand(2,10));
        logOutCommand(controller);

        // Log in with consumer account and make bookings for each event
        consumerLogIn(controller);
        long bookingNumber1 = consumerBookingOneTicket(controller,eventNumber1,performanceNumber1);
        long bookingNumber2 = consumerBookingOneTicket(controller,eventNumber2,performanceNumber2);
        long bookingNumber3 = consumerBookingOneTicket(controller,eventNumber3,performanceNumber3);
        logOutCommand(controller);

        // Log in with Entertainment Provider 1 and cancel one event
        EP1LogIn(controller);
        controller.runCommand(new CancelEventCommand(eventNumber2,"Test event is cancelled."));
        logOutCommand(controller);

        // Log in with government representative account and request the booking records
        govRepLogIn(controller);
        GovernmentReport1Command cmd = new GovernmentReport1Command(LocalDateTime.now(), LocalDateTime.now().plusMonths(3));
        controller.runCommand(cmd);
        List<Booking> bookings = cmd.getResult();

        assertEquals(1, bookings.size());
        assertEquals(bookingNumber3, bookings.get(0).getBookingNumber());
        assertEquals("juliustemitope17@gmail.com", bookings.get(0).getBooker().getEmail());
        assertEquals(eventNumber3, bookings.get(0).getEventPerformance().getEvent().getEventNumber());
        assertEquals(performanceNumber3, bookings.get(0).getEventPerformance().getPerformanceNumber());
    }
}
