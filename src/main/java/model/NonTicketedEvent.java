package model;

import java.util.HashMap;
import java.util.Map;

public class NonTicketedEvent extends Event {


    public NonTicketedEvent(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
        super(eventNumber, organiser, title,type);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
