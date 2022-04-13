package command;

import controller.Context;
import model.Event;
import model.EventPerformance;
import model.TicketedEvent;

public class GetAvailablePerformanceTicketsCommand extends Object implements ICommand{

    private final long eventNumber;
    private final long performanceNumber;
    private Integer tickets;

    public GetAvailablePerformanceTicketsCommand(long eventNumber,
                                                 long performanceNumber){
        this.eventNumber = eventNumber;
        this.performanceNumber = performanceNumber;
    }

    @Override
    public void execute(Context context) {
        Event currentEvent = context.getEventState().findEventByNumber(eventNumber);
        EventPerformance currentPerformance = currentEvent.getPerformanceByNumber(performanceNumber);

        if (currentPerformance != null && currentEvent != null && currentEvent instanceof TicketedEvent) {
            // i dont think this is supposed to be implemented like this
            tickets = ((TicketedEvent) currentEvent).getNumTickets();
        }
    }

    public Integer getResult(){
        if (tickets != null) {
            return tickets;
        } else {
            return null;
        }
    }
}
