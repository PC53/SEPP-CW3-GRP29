package command;

import controller.Context;
import model.EntertainmentProvider;
import model.EventType;
import model.User;

public abstract class CreateEventCommand extends Object implements ICommand{

    protected Long eventNumberResult;
    protected final String title;
    protected final EventType type;

    public CreateEventCommand(String title,
                              EventType type){
        this.title = title;
        this.type = type;
    }

    protected boolean isUserAllowedToCreateEvent(Context context){
        User currentUser = context.getUserState().getCurrentUser();
        if (currentUser != null && (currentUser instanceof EntertainmentProvider)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Long getResult() {
        if (eventNumberResult != null) {
            return eventNumberResult;
        } else {
            return null;
        }
    }
}
//merge test