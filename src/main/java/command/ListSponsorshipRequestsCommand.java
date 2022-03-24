package command;

import controller.Context;

import java.util.List;

public class ListSponsorshipRequestsCommand extends Object implements ICommand{
    private final boolean pendingRequestsOnly;

    public ListSponsorshipRequestsCommand(boolean pendingRequestsOnly){
        this.pendingRequestsOnly = pendingRequestsOnly;
    }

    @Override
    public void execute(Context context) {

    }

    @Override
    public List<SponsorshipRequest> getResult() {
        return null;
    }
}
