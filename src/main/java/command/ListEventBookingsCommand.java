package command;

import controller.Context;
import model.Booking;
import java.util.List;

public class ListEventBookingsCommand extends Object implements ICommand{

    private final long eventNumber;
    public ListEventBookingsCommand(long eventNumber){
        this.eventNumber = eventNumber;
    }


    @Override
    public void execute(Context context) {

    }

    @Override
    public List<Booking> getResult() {
        return null;
    }
}
