package state;

import model.EntertainmentProvider;
import model.Event;
import model.EventType;
import model.EventPerformance;
import model.TicketedEvent;
import model.NonTicketedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventState extends Object implements IEventState{

    List<Event> events;
    private long nextEventNumber;
    private long nextPerformanceNumber;

    public EventState() {
        this.events = new ArrayList<>();
        this.nextEventNumber = 1;
        this.nextPerformanceNumber = 1;
    }

    public EventState(IEventState other) {
        this.events = new ArrayList<>(((EventState)other).events);
        this.nextEventNumber = ((EventState)other).nextEventNumber;
        this.nextPerformanceNumber = ((EventState)other).nextPerformanceNumber;
    }

    @Override
    public EventPerformance createEventPerformance(Event event,
                                                   String venueAddress,
                                                   LocalDateTime startDateTime,
                                                   LocalDateTime endDateTime,
                                                   List<String> performerNames,
                                                   boolean hasSocialDistancing,
                                                   boolean hasAirFiltration,
                                                   boolean isOutdoors,
                                                   int capacityLimit,
                                                   int venueSize) {
        EventPerformance instance = new EventPerformance(nextPerformanceNumber, event,venueAddress,startDateTime,
                endDateTime,performerNames, hasSocialDistancing, hasAirFiltration, isOutdoors, capacityLimit,venueSize);
        event.addPerformance(instance);
        nextPerformanceNumber++;
        return instance;
    }

    @Override
    public NonTicketedEvent createNonTicketedEvent(EntertainmentProvider organiser, String title, EventType type) {
        NonTicketedEvent instance = new NonTicketedEvent(nextEventNumber, organiser, title, type);
        events.add(instance);
        nextEventNumber++;
        return instance;
    }

    @Override
    public TicketedEvent createTicketedEvent(EntertainmentProvider organiser,
                                             String title,
                                             EventType type,
                                             double ticketPrice,
                                             int numTickets) {
        TicketedEvent instance = new TicketedEvent(nextEventNumber, organiser, title, type, ticketPrice, numTickets);
        events.add(instance);
        nextEventNumber++;
        return instance;
    }

    @Override
    public Event findEventByNumber(long eventNumber) {
        for (int i = 0; i < events.size(); i++) {
            if (Objects.equals(events.get(i).getEventNumber(), eventNumber)) {
                return events.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Event> getAllEvents() {
        return events;
    }
}
