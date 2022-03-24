package command;

import controller.Context;

import java.time.LocalDateTime;
import java.util.List;

import model.Booking;

public class GovernmentReport1Command extends Object implements ICommand{

    private final LocalDateTime intervalStartInclusive;
    private final LocalDateTime intervalEndInclusive;

    public GovernmentReport1Command(LocalDateTime intervalStartInclusive,
                                    LocalDateTime intervalEndInclusive){
        this.intervalStartInclusive = intervalStartInclusive;
        this.intervalEndInclusive = intervalEndInclusive;
    }

    @Override
    public void execute(Context context) {

    }

    @Override
    public List<Booking> getResult() {
        return null;
    }
}
