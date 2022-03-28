package command;

import controller.Context;
import model.User;

public class CancelEventCommand extends Object implements ICommand{

    private final String organiserMessage;
    private final long eventNumber;

    public CancelEventCommand(long eventNumber,
                              String organiserMessage){
        this.eventNumber = eventNumber;
        this.organiserMessage = organiserMessage;
    }
    
    @Override
    public void execute(Context context) {
        if(! organiserMessage.equals("")){
            User currUser = context.getUserState().getCurrentUser();
            if()
        }
    }

    @Override
    public Object getResult() {
        return null;
    }
}
