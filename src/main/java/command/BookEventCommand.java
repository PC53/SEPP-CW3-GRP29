package command;

import controller.Context;

public class BookEventCommand extends Object implements ICommand{
    private final long eventNumber;
    private final long performanceNumber;
    private final int numTicketsRequested;

    public BookEventCommand(long eventNumber,
                            long performanceNumber,
                            int numTicketsRequested){
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
        this.numTicketsRequested = numTicketsRequested;
    }

    @Override
    public void execute(Context context) {

    }

    @Override
    public Long getResult() {
        return null;
    }
}
