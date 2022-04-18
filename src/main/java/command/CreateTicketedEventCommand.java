package command;

import controller.Context;
import logging.Logger;
import model.*;

public class CreateTicketedEventCommand extends CreateEventCommand{

    enum LogStatus{
        CREATE_TICKETED_EVENT_SUCCESS,
        CREATE_EVENT_REQUESTED_SPONSORSHIP
    }

    private void logResult(CreateTicketedEventCommand.LogStatus status){
        Logger.getInstance().logAction("command.CreateTicketedEventCommand",status);
    }


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
        if (isUserAllowedToCreateEvent(context)) {

            EntertainmentProvider currentUser =(EntertainmentProvider) context.getUserState().getCurrentUser();

            TicketedEvent newTicketedEvent = context.getEventState().createTicketedEvent(currentUser, title, type, ticketPrice, numTickets);
            super.eventNumberResult = newTicketedEvent.getEventNumber();

            if(requestSponsorship){
                context.getSponsorshipState().addSponsorshipRequest(newTicketedEvent);
                logResult(LogStatus.CREATE_EVENT_REQUESTED_SPONSORSHIP);
            }

            // record in Entertainment provider system
            currentUser.getProviderSystem().recordNewEvent(newTicketedEvent.getEventNumber(),
                                                            title,
                                                            numTickets);
            currentUser.addEvent(newTicketedEvent);

            logResult(LogStatus.CREATE_TICKETED_EVENT_SUCCESS);
        }
    }
}
