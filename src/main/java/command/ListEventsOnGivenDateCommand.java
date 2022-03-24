package command;

import controller.Context;

import java.time.LocalDateTime;

public class ListEventsOnGivenDateCommand extends Object implements ICommand{

    private final boolean userEventsOnly;
    private final boolean activeEventsOnly;
    private final LocalDateTime searchDateTime;

    public ListEventsOnGivenDateCommand(boolean userEventsOnly,
                                        boolean activeEventsOnly,
                                        LocalDateTime searchDateTime){
        this.userEventsOnly = userEventsOnly;
        this.activeEventsOnly = activeEventsOnly;
        this.searchDateTime = searchDateTime;
    }

    @Override
    public void execute(Context context) {

    }

    @Override
    public Object getResult() {
        return null;
    }
}
