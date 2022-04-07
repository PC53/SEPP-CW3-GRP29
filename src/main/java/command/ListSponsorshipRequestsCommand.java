package command;

import controller.Context;
import model.GovernmentRepresentative;
import model.SponsorshipRequest;
import model.User;

import java.util.List;

public class ListSponsorshipRequestsCommand extends Object implements ICommand{
    private final boolean pendingRequestsOnly;
    private List<SponsorshipRequest> requests;

    public ListSponsorshipRequestsCommand(boolean pendingRequestsOnly){
        this.pendingRequestsOnly = pendingRequestsOnly;
    }

    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        if(user != null){
            if(user instanceof GovernmentRepresentative){
                if(pendingRequestsOnly){
                    requests = context.getSponsorshipState().getPendingSponsorshipRequests();
                }
                else{
                    requests = context.getSponsorshipState().getAllSponsorshipRequests();
                }
            }

        }
    }

    @Override
    public List<SponsorshipRequest> getResult() {
        return requests;
    }
}
