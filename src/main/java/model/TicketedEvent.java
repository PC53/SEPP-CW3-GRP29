package model;

public class TicketedEvent extends Event {
    private final double ticketPrice;
    private final int numTickets;

    private SponsorshipRequest sponsorshipRequest;

    public TicketedEvent(long eventNumber,
                         EntertainmentProvider organiser,
                         String title,
                         EventType type,
                         double ticketPrice,
                         int numTickets)
    {
        super(eventNumber, organiser, title, type);
        this.ticketPrice = ticketPrice;
        this.numTickets = numTickets;
    }

    public double getOriginalTicketPrice() {
        return 0;
    }

    public double getDiscountedTicketPrice() {
        return 0;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public String getSponsorAccountEmail() {
        return null;
    }

    public boolean isSponsored() {
        return false;
    }

    public void setSponsorshipRequest(SponsorshipRequest sponsorshipRequest) {

    }

    @Override
    public String toString() {
        return null;
    }

}
