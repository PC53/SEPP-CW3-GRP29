package command;

import controller.Context;

public class RespondSponsorshipCommand extends Object implements ICommand{

    private final long requestNumber;
    private final int percentToSponsor;

    public RespondSponsorshipCommand(long requestNumber,
                                      int percentToSponsor){
        this.requestNumber = requestNumber;
        this.percentToSponsor = percentToSponsor;
    }

    @Override
    public void execute(Context context) {

    }

    @Override
    public Boolean getResult() {
        return null;
    }
}
