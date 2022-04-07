package command;

import controller.Context;
import model.Booking;
import model.Consumer;
import model.User;

import java.util.List;

public class ListConsumerBookingsCommand extends Object implements ICommand{

    private List<Booking> bookings;

    public ListConsumerBookingsCommand(){

    }

    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        if(user != null){
            if(user instanceof Consumer){
                bookings = ((Consumer) user).getBookings();
            }
        }
    }

    @Override
    public List<Booking> getResult() {
        return bookings;
    }
}
