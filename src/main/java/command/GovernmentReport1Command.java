package command;

import controller.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class GovernmentReport1Command extends Object implements ICommand{

    private final LocalDateTime intervalStartInclusive;
    private final LocalDateTime intervalEndInclusive;
    private List<Booking> output;

    public GovernmentReport1Command(LocalDateTime intervalStartInclusive,
                                    LocalDateTime intervalEndInclusive){
        this.intervalStartInclusive = intervalStartInclusive;
        this.intervalEndInclusive = intervalEndInclusive;
        this.output = new ArrayList<Booking>();
    }

    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();

        if(intervalEndInclusive.isBefore(intervalStartInclusive) || intervalEndInclusive.isEqual(intervalStartInclusive)){
            return;
        }

        if (user instanceof GovernmentRepresentative) {
            // looping through all the events adding bookings in the interval
            for (Event event : context.getEventState().getAllEvents()) {
                long eventNumber = event.getEventNumber();
                boolean status = false;
                List<SponsorshipRequest> requests = context.getSponsorshipState().getAllSponsorshipRequests();
                for (SponsorshipRequest request : requests) {
                    if (request.getEvent().getEventNumber() == eventNumber) {
                        if (request.getStatus() == SponsorshipStatus.ACCEPTED) {
                            status = true;
                            break;
                        }
                    }
                }
                if (event instanceof TicketedEvent && status) {
                    List<Booking> bookings = context.getBookingState().findBookingsByEventNumber(eventNumber);
                    for (Booking booking : bookings) {
                        EventPerformance ep = booking.getEventPerformance();
                        if (!ep.getStartDateTime().isBefore(intervalStartInclusive)
                                && !ep.getEndDateTime().isAfter(intervalEndInclusive)
                                && booking.getStatus() == BookingStatus.Active) {
                            output.add(booking);
                        }
                    }
                }
            }
        } else {
            return;
        }
    }

    @Override
    public List<Booking> getResult() {
        return output;
    }
}
