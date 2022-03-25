package model;

import java.util.Collection;
import java.util.Map;

public abstract class Event extends Object {
    private final long eventNumber;
    private final EntertainmentProvider organiser;
    private final String title;
    private final EventType type;

    private Map<Long, EventPerformance> performances;
    private EventStatus status;

    private EventType attribute;
    private EventStatus attribute2;


    protected Event(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
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

    }

    public void addPerformance(EventPerformance performance) {

    }

    public EventPerformance getPerformanceByNumber(long performanceNumber) {
        return null;
    }

    public Collection<EventPerformance> getPerformances() {
        return null;
    }

}
