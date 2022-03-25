package state;

import model.SponsorshipRequest;
import model.TicketedEvent;

import java.util.ArrayList;
import java.util.List;

public class SponsorshipState extends Object implements ISponsorshipState{

    private long nextRequestNumber;
    private List<SponsorshipRequest> sponsorshipRequests;

    public SponsorshipState() {
        this.nextRequestNumber = 1;
        this.sponsorshipRequests = new ArrayList<>();
    }

    public SponsorshipState(ISponsorshipState other) {
        this.nextRequestNumber = ((SponsorshipState)other).nextRequestNumber;
        this.sponsorshipRequests = new ArrayList<>(((SponsorshipState)other).sponsorshipRequests);
    }

    @Override
    public SponsorshipRequest addSponsorshipRequest(TicketedEvent event) {
        return null;
    }

    @Override
    public SponsorshipRequest findRequestByNumber(long requestNumber) {
        return null;
    }

    @Override
    public List<SponsorshipRequest> getAllSponsorshipRequests() {
        return null;
    }

    @Override
    public List<SponsorshipRequest> getPendingSponsorshipRequests() {
        return null;
    }
}
