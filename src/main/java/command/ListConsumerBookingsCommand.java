package command;

import controller.Context;
import model.Booking;

import java.util.List;

public class ListConsumerBookingsCommand extends Object implements ICommand{

    public ListConsumerBookingsCommand(){

    }

    @Override
    public void execute(Context context) {

    }

    @Override
    public List<Booking> getResult() {
        return null;
    }
}
