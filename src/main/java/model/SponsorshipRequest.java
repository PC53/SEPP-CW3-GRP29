package model;

public class SponsorshipRequest extends Object {
    private final long requestNumber;
    private final TicketedEvent event;

    private SponsorshipStatus status;
    private Integer sponsoredPricePercent;

    private String sponsorAccountEmail;
    private TicketedEvent attribute;
    private SponsorshipStatus attribute2;


    public SponsorshipRequest(long requestNumber, TicketedEvent event) {
        this.requestNumber = requestNumber;
        this.event = event;
        //status = SponsorshipStatus.PENDING;
    }

    public long getRequestNumber() {
        return requestNumber;
    }

    public TicketedEvent getEvent() {
        return event;
    }

    public SponsorshipStatus getStatus() {
        return null;
    }

    public Integer getSponsoredPricePercent() {
        return null;
    }

    public String getSponsorAccountEmail() {
        return null;
    }

    public void accept(int percent, String sponsorAccountEmail) {

    }

    public void reject(){

    }


}
