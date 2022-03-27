package state;

import model.SponsorshipRequest;
import model.TicketedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        SponsorshipRequest instance = new SponsorshipRequest(nextRequestNumber, event);
        sponsorshipRequests.add(instance);
        event.setSponsorshipRequest(instance);
        nextRequestNumber++;
        return instance;
    }

    @Override
    public SponsorshipRequest findRequestByNumber(long requestNumber) {
        for (int i = 0; i < sponsorshipRequests.size(); i++) {
            if (Objects.equals(sponsorshipRequests.get(i).getRequestNumber(), requestNumber)) {
                return sponsorshipRequests.get(i);
            }
        }
        return null;
    }

    @Override
    public List<SponsorshipRequest> getAllSponsorshipRequests() {
        return sponsorshipRequests;
    }

    @Override
    public List<SponsorshipRequest> getPendingSponsorshipRequests() {
        List<SponsorshipRequest> PendingSponsorshipRequests = new ArrayList<>();
        for (int i = 0; i < sponsorshipRequests.size(); i++) {
            if (Objects.equals(sponsorshipRequests.get(i).getStatus(), false)) { // false -> SponsorshipState.PENDING
                PendingSponsorshipRequests.add(sponsorshipRequests.get(i));
            }
        }
        return PendingSponsorshipRequests;
    }
}
