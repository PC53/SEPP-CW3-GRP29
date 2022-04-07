package command;

import controller.Context;
import model.*;

import java.util.List;

public class ListEventBookingsCommand extends Object implements ICommand{

    private final long eventNumber;
    private List<Booking> bookings;
    public ListEventBookingsCommand(long eventNumber){
        this.eventNumber = eventNumber;
    }


    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        if(bookings != null){
            Event event = context.getEventState().findEventByNumber(eventNumber);
            if(event instanceof TicketedEvent){
                if(user instanceof GovernmentRepresentative || user.equals(event.getOrganiser())){
                    bookings = context.getBookingState().findBookingsByEventNumber(eventNumber);
                }
            }
        }
    }

    @Override
    public List<Booking> getResult() {
        return bookings;
    }
}
