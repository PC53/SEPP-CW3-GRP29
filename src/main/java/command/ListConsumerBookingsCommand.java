package command;

import controller.Context;
import logging.Logger;
import model.Booking;
import model.Consumer;
import model.User;

import java.util.List;

public class ListConsumerBookingsCommand extends Object implements ICommand{

    enum LogStatus{
        LIST_CONSUMER_BOOKINGS_NOT_LOGGED_IN,
        LIST_CONSUMER_BOOKINGS_USER_NOT_CONSUMER,
                LIST_CONSUMER_BOOKINGS_SUCCESS,
    }

    private void logResult(ListConsumerBookingsCommand.LogStatus status){
        Logger.getInstance().logAction("command.ListConsumerBookingsCommand",status);
    }

    private List<Booking> bookings;

    public ListConsumerBookingsCommand(){

    }

    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        if(user != null){
            if(user instanceof Consumer){
                bookings = ((Consumer) user).getBookings();
                logResult(LogStatus.LIST_CONSUMER_BOOKINGS_SUCCESS);
            }else logResult(LogStatus.LIST_CONSUMER_BOOKINGS_USER_NOT_CONSUMER);
        }else logResult(LogStatus.LIST_CONSUMER_BOOKINGS_NOT_LOGGED_IN);
    }

    @Override
    public List<Booking> getResult() {
        return bookings;
    }
}
