package state;

import model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface IEventState {

    /**
     *
     * @param event
     * @param venueAddress
     * @param startDateTime
     * @param endDateTime
     * @param performerNames
     * @param hasSocialDistancing
     * @param hasAirFiltration
     * @param isOutdoors
     * @param capacityLimit
     * @param venueSize
     * @return
     */
    EventPerformance createEventPerformance(Event event, String venueAddress, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                            List<String> performerNames, boolean hasSocialDistancing, boolean hasAirFiltration,
                                            boolean isOutdoors, int capacityLimit, int venueSize);

    /**
     *
     * @param organiser
     * @param title
     * @param type
     * @return
     */
    NonTicketedEvent createNonTicketedEvent(EntertainmentProvider organiser, String title, EventType type);

    /**
     *
     * @param organiser
     * @param title
     * @param type
     * @param ticketPrice
     * @param numTickets
     * @return
     */
    TicketedEvent createTicketedEvent(EntertainmentProvider organiser, String title, EventType type, double ticketPrice, int numTickets);

    /**
     *
     * @param eventNumber
     * @return
     */
    Event findEventByNumber(long eventNumber);

    /**
     *
     * @return
     */
    List<Event> getAllEvents();
}
