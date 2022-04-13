package model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Event extends Object {
    private final long eventNumber;
    private final EntertainmentProvider organiser;
    private final String title;
    private final EventType type;
    // performance number
    private Map<Long, EventPerformance> performances;
    private EventStatus status;

    private EventType attribute;
    private EventStatus attribute2;


    protected Event(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
        this.performances = new HashMap<Long,EventPerformance>();
        this.eventNumber = eventNumber;
        this.organiser = organiser;
        this.title = title;
        this.type = type;
    }

    public long getEventNumber() {
        return eventNumber;
    }

    public EntertainmentProvider getOrganiser() {
        return organiser;
    }

    public String getTitle() {
        return title;
    }

    public EventType getType() {
        return type;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void cancel() {
        status = EventStatus.CANCELLED;
    }

    public void addPerformance(EventPerformance performance) {
        // using the performance number as a key.
        performances.put(performance.getPerformanceNumber(), performance);

    }

    public EventPerformance getPerformanceByNumber(long performanceNumber) {
        return performances.get(performanceNumber);
    }

    public Collection<EventPerformance> getPerformances() {
        return performances.values();
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventNumber=" + eventNumber +
                ", organiser=" + organiser.getOrgName() +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", performances=" + performances +
                ", status=" + status +
                ", attribute=" + attribute +
                ", attribute2=" + attribute2 +
                '}';
    }
}
