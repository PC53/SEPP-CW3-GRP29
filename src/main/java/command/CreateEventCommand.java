package command;

import controller.Context;
import model.EventType;

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
        return true;
    }

    @Override
    public Long getResult() {
        return null;
    }
}
