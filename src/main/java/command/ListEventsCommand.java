package command;

import controller.Context;
import jdk.jfr.Event;

import java.util.List;

public class ListEventsCommand extends Object implements ICommand{

    private final boolean userEventsOnly;
    private final boolean activeEventsOnly;

    public ListEventsCommand(boolean userEventsOnly,
                             boolean activeEventsOnly){
        this.userEventsOnly = userEventsOnly;
        this.activeEventsOnly = activeEventsOnly;
    }

    @Override
    public void execute(Context context){

    }

    @Override
    public Object getResult() {
        return null;
    }
}
