package command;

import controller.Context;
import logging.Logger;
import model.GovernmentRepresentative;
import model.SponsorshipRequest;
import model.User;

import java.util.List;

public class ListSponsorshipRequestsCommand extends Object implements ICommand{

    enum LogStatus{
        LIST_SPONSORSHIP_REQUESTS_NOT_LOGGED_IN,
        LIST_SPONSORSHIP_REQUESTS_NOT_GOVERNMENT_REPRESENTATIVE
    }

    private void logResult(ListSponsorshipRequestsCommand.LogStatus status){
        Logger.getInstance().logAction("command.ListSponsorshipRequestsCommand",status);
    }

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
            }else logResult(LogStatus.LIST_SPONSORSHIP_REQUESTS_NOT_GOVERNMENT_REPRESENTATIVE);

        }else logResult(LogStatus.LIST_SPONSORSHIP_REQUESTS_NOT_LOGGED_IN);
    }

    @Override
    public List<SponsorshipRequest> getResult() {
        return requests;
    }
}
