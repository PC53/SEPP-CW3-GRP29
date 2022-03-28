package command;

import controller.Context;
import jdk.jfr.EventType;

public class CreateEventCommand extends Object implements ICommand{

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
    public void execute(Context context) {

    }

    @Override
    public Long getResult() {
        return null;
    }
}
