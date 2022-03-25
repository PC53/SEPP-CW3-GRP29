package state;

import model.SponsorshipRequest;
import model.TicketedEvent;

import java.util.List;

public interface ISponsorshipState {

    /**
     *
     * @param event
     * @return
     */
    SponsorshipRequest addSponsorshipRequest(TicketedEvent event);

    /**
     *
     * @param requestNumber
     * @return
     */
    SponsorshipRequest findRequestByNumber(long requestNumber);

    /**
     *
     * @return
     */
    List<SponsorshipRequest> getAllSponsorshipRequests();

    /**
     *
     * @return
     */
    List<SponsorshipRequest> getPendingSponsorshipRequests();

}
