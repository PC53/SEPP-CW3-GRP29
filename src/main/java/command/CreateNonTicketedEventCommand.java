package command;

import controller.Context;
import model.*;

import java.util.List;

public class CreateNonTicketedEventCommand extends CreateEventCommand{

    public CreateNonTicketedEventCommand(String title,
                                         EventType type){
        // check: not sure if super part is correct
        super(title, type);
    }

    @Override
    public void execute(Context context) {
        User currentUser = context.getUserState().getCurrentUser();

        if (currentUser != null && (currentUser instanceof EntertainmentProvider)) {
            NonTicketedEvent newNonTicketedEvent = context.getEventState().createNonTicketedEvent((EntertainmentProvider) currentUser, title, type);
            super.eventNumberResult = newNonTicketedEvent.getEventNumber();
        }
    }
}
