package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.util.List;

public class CreateNonTicketedEventCommand extends CreateEventCommand{

    enum LogStatus{
        CREATE_NON_TICKETED_EVENT_SUCCESS
    }

    private void logResult(CreateNonTicketedEventCommand.LogStatus status){
        Logger.getInstance().logAction("command.CreateNonTicketedCommand",status);
    }


    public CreateNonTicketedEventCommand(String title,
                                         EventType type){
        super(title, type);
    }

    @Override
    public void execute(Context context) {

        if (isUserAllowedToCreateEvent(context)) {
            EntertainmentProvider currentUser =(EntertainmentProvider) context.getUserState().getCurrentUser();

            NonTicketedEvent newNonTicketedEvent = context.getEventState().createNonTicketedEvent(currentUser, title, type);
            super.eventNumberResult = newNonTicketedEvent.getEventNumber();

            // record in Entertainment provider system
            currentUser.getProviderSystem().recordNewEvent(newNonTicketedEvent.getEventNumber(),
                                                            title,0);

            currentUser.addEvent(newNonTicketedEvent);

            logResult(LogStatus.CREATE_NON_TICKETED_EVENT_SUCCESS);
        }
    }
}
