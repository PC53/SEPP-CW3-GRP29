package command;

import controller.Context;
import jdk.jfr.EventType;

public class CreateTicketedEventCommand extends CreateEventCommand{

    private final int numTickets;
    private final double ticketPrice;
    private final boolean requestSponsorship;
    public CreateTicketedEventCommand(String title,
                                      EventType type,
                                      int numTickets,
                                      double ticketPrice,
                                      boolean requestSponsorship){
        super(title, type);
        this.numTickets = numTickets;
        this.ticketPrice = ticketPrice;
        this.requestSponsorship = requestSponsorship;
    }

    @Override
    public void execute(Context context) {

    }
}
