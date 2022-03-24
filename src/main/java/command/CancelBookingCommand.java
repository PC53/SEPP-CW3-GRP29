package command;

import controller.Context;

public class CancelBookingCommand extends Object implements ICommand{
    private final long bookingNumber;
    public CancelBookingCommand(long bookingNumber){
        this.bookingNumber = bookingNumber;
    }

    @Override
    public void execute(Context context) {

    }

    @Override
    public Boolean getResult() {
        return null;
    }
}
