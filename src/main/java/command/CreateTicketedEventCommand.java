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
        super(title, type);
        this.numTickets = numTickets;
        this.ticketPrice = ticketPrice;
        this.requestSponsorship = requestSponsorship;
    }

    @Override
    public void execute(Context context) {
        User currentUser = context.getUserState().getCurrentUser();
        if (currentUser != null && (currentUser instanceof EntertainmentProvider)) {

            TicketedEvent newTicketedEvent = context.getEventState().createTicketedEvent((EntertainmentProvider) currentUser, title, type, ticketPrice, numTickets);
            super.eventNumberResult = newTicketedEvent.getEventNumber();

            if(requestSponsorship){
                context.getSponsorshipState().addSponsorshipRequest(newTicketedEvent);
            }
        }
    }
}
