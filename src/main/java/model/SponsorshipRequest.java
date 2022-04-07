package model;

public class SponsorshipRequest extends Object {
    private final long requestNumber;
    private final TicketedEvent event;

    private SponsorshipStatus status;
    private Integer sponsoredPricePercent;

    private String sponsorPayAccountEmail;

    private TicketedEvent attribute;
    private SponsorshipStatus attribute2; // this isn't needed here??


    public SponsorshipRequest(long requestNumber, TicketedEvent event) {
        this.requestNumber = requestNumber;
        this.event = event;
        status = SponsorshipStatus.PENDING;
    }

    public long getRequestNumber() {
        return requestNumber;
    }

    public TicketedEvent getEvent() {
        return event;
    }

    public SponsorshipStatus getStatus() {
        return status;
    }

    public Integer getSponsoredPricePercent() {
        if (status == SponsorshipStatus.ACCEPTED) {
            return sponsoredPricePercent;
        }
        else { return null; }
    }

    public String getSponsorAccountEmail() {
        return sponsorPayAccountEmail;
    }

    public void accept(int percent, String sponsorAccountEmail) {
        status = SponsorshipStatus.ACCEPTED;
        sponsoredPricePercent = percent;
        sponsorPayAccountEmail = sponsorAccountEmail;

    }

    public void reject(){
        status = SponsorshipStatus.REJECTED;
    }


    @Override
    public String toString() {
        return "SponsorshipRequest{" +
                "requestNumber=" + requestNumber +
                ", event=" + event +
                ", status=" + status +
                ", sponsoredPricePercent=" + sponsoredPricePercent +
                ", sponsorPayAccountEmail='" + sponsorPayAccountEmail + '\'' +
                ", Event=" + attribute.getTitle() +
                '}';
    }
}
