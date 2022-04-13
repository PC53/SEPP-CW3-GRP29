package command;

import controller.Context;
import model.*;

public class CreateTicketedEventCommand extends CreateEventCommand{

    private final int numTickets;
    private final double ticketPrice;
    private final boolean requestSponsorship;
    public CreateTicketedEventCommand(String title,
                                      EventType type,
                                      int numTickets,
                                      double ticketPrice,
                                      boolean requestSponsorship){
        // not sure if the super is supposed to be here
        super(title, type);
        this.numTickets = numTickets;
        this.ticketPrice = ticketPrice;
        this.requestSponsorship = requestSponsorship;
    }

    @Override
    public void execute(Context context) {
        User currentUser = context.getUserState().getCurrentUser();
        if (currentUser != null && (currentUser instanceof EntertainmentProvider)) {
            // what happens to the government sponsorship request?
            TicketedEvent newTicketedEvent = context.getEventState().createTicketedEvent((EntertainmentProvider) currentUser, title, type, ticketPrice, numTickets);
            super.eventNumberResult = newTicketedEvent.getEventNumber();
        }
    }
}
