package command;

import controller.Context;

public class GetAvailablePerformanceTicketsCommand extends Object implements ICommand{

    private final long eventNumber;
    private final long performanceNumber;

    public GetAvailablePerformanceTicketsCommand(long eventNumber,
                                                 long performanceNumber){
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
    }

    @Override
    public void execute(Context context) {

    }

    public Integer getResult(){
        return null;
    }
}
