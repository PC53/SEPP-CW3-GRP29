package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.util.List;

public class ListEventBookingsCommand extends Object implements ICommand{

    enum LogStatus{
        LIST_EVENT_BOOKINGS_USER_NOT_LOGGED_IN,
        LIST_EVENT_BOOKINGS_EVENT_NOT_TICKETED,
                LIST_EVENT_BOOKINGS_SUCCESS,
        LIST_EVENT_BOOKINGS_EVENT_NOT_FOUND,
                LIST_EVENT_BOOKINGS_USER_NOT_ORGANISER_NOR_GOV
    }

    private void logResult(ListEventBookingsCommand.LogStatus status){
        Logger.getInstance().logAction("command.ListEventBookingsCommand",status);
    }

    private final long eventNumber;
    private List<Booking> bookings;
    public ListEventBookingsCommand(long eventNumber){
        this.eventNumber = eventNumber;
    }


    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        if(user == null){
            logResult(LogStatus.LIST_EVENT_BOOKINGS_USER_NOT_LOGGED_IN);
            return;
        }
        if(bookings != null){
            Event event = context.getEventState().findEventByNumber(eventNumber);
            if(event instanceof TicketedEvent){
                if(user instanceof GovernmentRepresentative || user.equals(event.getOrganiser())){
                    bookings = context.getBookingState().findBookingsByEventNumber(eventNumber);
                    logResult(LogStatus.LIST_EVENT_BOOKINGS_SUCCESS);
                }else logResult(LogStatus.LIST_EVENT_BOOKINGS_USER_NOT_ORGANISER_NOR_GOV);
            }else logResult(LogStatus.LIST_EVENT_BOOKINGS_EVENT_NOT_TICKETED);
        }else logResult(LogStatus.LIST_EVENT_BOOKINGS_EVENT_NOT_FOUND);
    }

    @Override
    public List<Booking> getResult() {
        return bookings;
    }
}
