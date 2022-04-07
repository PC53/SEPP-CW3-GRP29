package model;

import java.time.LocalDateTime;
import java.util.List;

public class EventPerformance extends Object{
    private final long performanceNumber;
    private final Event event;
    private final String venueAddress;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final List<String> performerNames;
    private final boolean hasSocialDistancing;
    private final boolean hasAirFiltration;
    private final boolean isOutdoors;
    private final int capacityLimit;
    private final int venueSize;


    public EventPerformance(long performanceNumber,
                            Event event,
                            String venueAddress,
                            LocalDateTime startDateTime,
                            LocalDateTime endDateTime,
                            List<String> performerNames,
                            boolean hasSocialDistancing,
                            boolean hasAirFiltration,
                            boolean isOutdoors,
                            int capacityLimit,
                            int venueSize)
    {
        this.performanceNumber = performanceNumber;
        this.event = event;
        this.venueAddress = venueAddress;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.performerNames = performerNames;
        this.hasSocialDistancing = hasSocialDistancing;
        this.hasAirFiltration = hasAirFiltration;
        this.isOutdoors = isOutdoors;
        this.capacityLimit = capacityLimit;
        this.venueSize = venueSize;

    }

    public long getPerformanceNumber() {
        return performanceNumber;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public boolean hasSocialDistancing() {
        return hasSocialDistancing;
    }

    public boolean hasAirFiltration() {
        return hasAirFiltration;
    }

    public boolean isOutdoors() {
        return isOutdoors;
    }

    public int getVenueSize() {
        return venueSize;
    }

    public int getCapacityLimit(){return capacityLimit;}

    @Override
    public String toString() {
        return "EventPerformance{" +
                "performanceNumber=" + performanceNumber +
                ", event=" + event +
                ", venueAddress='" + venueAddress + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", performerNames=" + performerNames +
                ", hasSocialDistancing=" + hasSocialDistancing +
                ", hasAirFiltration=" + hasAirFiltration +
                ", isOutdoors=" + isOutdoors +
                ", capacityLimit=" + capacityLimit +
                ", venueSize=" + venueSize +
                '}';
    }
}
