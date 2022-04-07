package model;

public class TicketedEvent extends Event {
    private final double ticketPrice;
    private final int numTickets;

    private SponsorshipRequest currentSponsorshipRequest;

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
        return ticketPrice;
    }

    public double getDiscountedTicketPrice() {
        if (this.isSponsored()) {
            return ticketPrice * currentSponsorshipRequest.getSponsoredPricePercent();
        }
        else { return ticketPrice; }
    }

    public int getNumTickets() {
        return numTickets;
    }

    public String getSponsorAccountEmail() {
        return currentSponsorshipRequest.getSponsorAccountEmail();
    }

    public boolean isSponsored() {
        return (currentSponsorshipRequest.getStatus() == SponsorshipStatus.ACCEPTED);
    }

    public void setSponsorshipRequest(SponsorshipRequest sponsorshipRequest) {
         currentSponsorshipRequest = sponsorshipRequest;
    }

    @Override
    public String toString() {
        return "TicketedEvent{" +
                "eventNumber=" + super.getEventNumber() +
                ", organiser=" + super.getOrganiser().getOrgName() +
                ", title='" + super.getTitle() + '\'' +
                ", type=" + super.getType() +
                "ticketPrice=" + ticketPrice +
                ", numTickets=" + numTickets +
                ", currentSponsorshipRequest=" + currentSponsorshipRequest +
                '}';
    }
}
