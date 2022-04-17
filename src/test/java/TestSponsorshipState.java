
import static org.junit.jupiter.api.Assertions.*;

import model.*;
import org.junit.jupiter.api.*;

import state.SponsorshipState;

import java.util.ArrayList;

public class TestSponsorshipState {

    // Things to test:
    // -initialise sponsorship state
    // -add sponsorship request
    // -find request by number
    // -get all sponsorship requests
    // -get pending sponsorship requests


    @Test
    @DisplayName("Test to add a valid sponsorship request for a ticketed event to Sponsorship State")
    void addSingleValidSponsorshipRequest() {
        SponsorshipState testSS = new SponsorshipState();

        EntertainmentProvider ep = new EntertainmentProvider(
                "test org",
                "org road",
                "testorg@gmail.com",
                "mr org",
                "mrorg@gmail.com",
                "orgsorgsorgs",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        TicketedEvent te = new TicketedEvent(
                1,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        testSS.addSponsorshipRequest(te);

    }

   @Test
   @DisplayName("Testing the adding of duplicate sponsorship requests")
   void addingDuplicateSponsorshipRequests() {
       SponsorshipState testSS = new SponsorshipState();

       EntertainmentProvider ep = new EntertainmentProvider(
               "test org",
               "org road",
               "testorg@gmail.com",
               "mr org",
               "mrorg@gmail.com",
               "orgsorgsorgs",
               new ArrayList<String>(),
               new ArrayList<String>()
       );

       TicketedEvent te = new TicketedEvent(
               1,
               ep,
               "test ticketed event",
               EventType.Movie,
               10,
               1
       );

       testSS.addSponsorshipRequest(te);
       testSS.addSponsorshipRequest(te);

       assertNotEquals(testSS.getAllSponsorshipRequests().get(0), testSS.getAllSponsorshipRequests().get(1));
   }

    @Test
    @DisplayName("Testing if an event sponsorship request can be found by valid request number")
    void findingARequestInRange1() {
        SponsorshipState testSS = new SponsorshipState();

        EntertainmentProvider ep = new EntertainmentProvider(
                "test org",
                "org road",
                "testorg@gmail.com",
                "mr org",
                "mrorg@gmail.com",
                "orgsorgsorgs",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        TicketedEvent te1 = new TicketedEvent(
                1,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te2 = new TicketedEvent(
                2,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te3 = new TicketedEvent(
                3,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        testSS.addSponsorshipRequest(te1);
        testSS.addSponsorshipRequest(te2);
        testSS.addSponsorshipRequest(te3);

        assertEquals(te1, testSS.findRequestByNumber(1).getEvent());
        assertEquals(te2, testSS.findRequestByNumber(2).getEvent());
        assertEquals(te3, testSS.findRequestByNumber(3).getEvent());
    }

    @Test
    @DisplayName("Testing null is returned if request number supplied is much greater than the highest request number")
    void findingARequestOutOfRange1() {
        SponsorshipState testSS = new SponsorshipState();

        EntertainmentProvider ep = new EntertainmentProvider(
                "test org",
                "org road",
                "testorg@gmail.com",
                "mr org",
                "mrorg@gmail.com",
                "orgsorgsorgs",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        TicketedEvent te1 = new TicketedEvent(
                1,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        testSS.addSponsorshipRequest(te1);

        assertNull(testSS.findRequestByNumber(999));
    }

    @Test
    @DisplayName("Testing null is returned if request number supplied is negative")
    void findingARequestOutOfRange2() {
        SponsorshipState testSS = new SponsorshipState();

        EntertainmentProvider ep = new EntertainmentProvider(
                "test org",
                "org road",
                "testorg@gmail.com",
                "mr org",
                "mrorg@gmail.com",
                "orgsorgsorgs",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        TicketedEvent te1 = new TicketedEvent(
                1,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        testSS.addSponsorshipRequest(te1);

        assertNull(testSS.findRequestByNumber(-1));
    }

    @Test
    @DisplayName("Test to ensure that all sponsorship requests are returned when all are pending")
    void returnAllSponsorRequests1() {
        SponsorshipState testSS = new SponsorshipState();

        ArrayList<SponsorshipRequest> listOfReqs = new ArrayList<SponsorshipRequest>();
        EntertainmentProvider ep = new EntertainmentProvider(
                "test org",
                "org road",
                "testorg@gmail.com",
                "mr org",
                "mrorg@gmail.com",
                "orgsorgsorgs",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        TicketedEvent te1 = new TicketedEvent(
                1,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te2 = new TicketedEvent(
                2,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te3 = new TicketedEvent(
                3,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        testSS.addSponsorshipRequest(te1);
        testSS.addSponsorshipRequest(te2);
        testSS.addSponsorshipRequest(te3);

        assertEquals(te1, testSS.getAllSponsorshipRequests().get(0).getEvent());
        assertEquals(te2, testSS.getAllSponsorshipRequests().get(1).getEvent());
        assertEquals(te3, testSS.getAllSponsorshipRequests().get(2).getEvent());
    }

    // Commented out as empty list should be returned not null.
    /*
    @Test
    @DisplayName("Test to ensure that null is returned when there are no sponsorship requests")
    void returnAllSponsorRequests2() {
        SponsorshipState testSS = new SponsorshipState();
        assertArrayEquals(new ArrayList<SponsorshipRequest>(), testSS.getAllSponsorshipRequests());
    }
    */

    @Test
    @DisplayName("Testing to check all initial requests are set to pending")
    void getPendingSponsorRequests1() {
        SponsorshipState testSS = new SponsorshipState();

        EntertainmentProvider ep = new EntertainmentProvider(
                "test org",
                "org road",
                "testorg@gmail.com",
                "mr org",
                "mrorg@gmail.com",
                "orgsorgsorgs",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        TicketedEvent te1 = new TicketedEvent(
                1,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te2 = new TicketedEvent(
                2,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te3 = new TicketedEvent(
                3,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        testSS.addSponsorshipRequest(te1);
        testSS.addSponsorshipRequest(te2);
        testSS.addSponsorshipRequest(te3);

        assertEquals(te1, testSS.getPendingSponsorshipRequests().get(0).getEvent());
        assertEquals(te2, testSS.getPendingSponsorshipRequests().get(1).getEvent());
        assertEquals(te3, testSS.getPendingSponsorshipRequests().get(2).getEvent());
    }

    @Test
    @DisplayName("Testing to check if a accepted request is excluded")
    void getPendingSponsorRequests2() {
        SponsorshipState testSS = new SponsorshipState();

        EntertainmentProvider ep = new EntertainmentProvider(
                "test org",
                "org road",
                "testorg@gmail.com",
                "mr org",
                "mrorg@gmail.com",
                "orgsorgsorgs",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        TicketedEvent te1 = new TicketedEvent(
                1,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te2 = new TicketedEvent(
                2,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te3 = new TicketedEvent(
                3,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        testSS.addSponsorshipRequest(te1);
        testSS.addSponsorshipRequest(te2);
        testSS.addSponsorshipRequest(te3);

        SponsorshipRequest sponsorshipReq1 = testSS.getAllSponsorshipRequests().get(0);
        sponsorshipReq1.accept(10, "test sponsor email");

        assertEquals(te2, testSS.getPendingSponsorshipRequests().get(0).getEvent());
        assertEquals(te3, testSS.getPendingSponsorshipRequests().get(1).getEvent());
    }

    @Test
    @DisplayName("Testing to check if a rejected request is excluded")
    void getPendingSponsorRequests3() {
        SponsorshipState testSS = new SponsorshipState();

        EntertainmentProvider ep = new EntertainmentProvider(
                "test org",
                "org road",
                "testorg@gmail.com",
                "mr org",
                "mrorg@gmail.com",
                "orgsorgsorgs",
                new ArrayList<String>(),
                new ArrayList<String>()
        );

        TicketedEvent te1 = new TicketedEvent(
                1,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te2 = new TicketedEvent(
                2,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        TicketedEvent te3 = new TicketedEvent(
                3,
                ep,
                "test ticketed event",
                EventType.Movie,
                10,
                1
        );

        testSS.addSponsorshipRequest(te1);
        testSS.addSponsorshipRequest(te2);
        testSS.addSponsorshipRequest(te3);

        SponsorshipRequest sponsorshipReq1 = testSS.getAllSponsorshipRequests().get(0);
        sponsorshipReq1.reject();

        assertEquals(te2, testSS.getPendingSponsorshipRequests().get(0).getEvent());
        assertEquals(te3, testSS.getPendingSponsorshipRequests().get(1).getEvent());
    }
}
