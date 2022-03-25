package model;

public class NonTicketedEvent extends Event {
    NonTicketedEvent(long eventNumber, EntertainmentProvider organiser, String title, EventType type) {
        // I assume this is what the documentation wants - but not sure.
        super(eventNumber, organiser, title, type);
    }

    @Override
    public String toString(){
        return null;
    }
}
