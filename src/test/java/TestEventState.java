import logging.Logger;
import model.EntertainmentProvider;
import model.EventType;
import model.NonTicketedEvent;
import org.junit.jupiter.api.*;
import state.EventState;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TestEventState {
    @BeforeEach
    void printTestName(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
    }

    @AfterEach
    void clearLogs() {
        Logger.getInstance().clearLog();
        System.out.println("---");
    }

    private static EntertainmentProvider newEntertainmentProvider() {
        ArrayList<String> repNames = new ArrayList<>();
        ArrayList<String> repEmails = new ArrayList<>();
        EntertainmentProvider entertainmentProvider = new EntertainmentProvider(
                "University of Edinburgh",
                "Edinburgh",
                "uoe@ed.ac.uk",
                "Ning Guang",
                "ningguang@ed.ac.uk",
                "i am very secure",
                repNames,
                repEmails
        );
        return entertainmentProvider;
    }

    @Test
    @DisplayName("Creating One Valid Non Ticketed Event")
    void creatingOneNonTicketedEvent() {
        EventState eventState= new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();

        NonTicketedEvent nonTicketedEvent = eventState.createNonTicketedEvent(
                entertainmentProvider,
                "Charity Run",
                EventType.Sports
        );

        assertEquals(eventState.getAllEvents().get(0), nonTicketedEvent);
    }

    @Test
    @DisplayName("Creating One Invalid Non Ticketed Event")
    void creatingInvalidNonTicketedEvent() {
        EventState eventState = new EventState();
        EntertainmentProvider entertainmentProvider = newEntertainmentProvider();

    }
}
