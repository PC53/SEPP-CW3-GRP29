package command;

import controller.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import logging.Logger;
import model.*;

/**
 * GovernmentReport1Command allows government representatives (when they are logged in) to see up-to-date information
 * about all the Bookings of performances that are between the two date-times given by government representatives and
 * belong to an active and sponsored event.
 */
public class GovernmentReport1Command extends Object implements ICommand{

    enum LogStatus{
        GOVERNMENT_REPORT1_NOT_LOGGED_IN,
        GOVERNMENT_REPORT1_USER_NOT_GOVERNMENT_REPRESENTATIVE,
                GOVERNMENT_REPORT1_SUCCESS
    }

    private void logResult(GovernmentReport1Command.LogStatus status){
        Logger.getInstance().logAction("command.GovernmentReport1Command",status);
    }

    private final LocalDateTime intervalStartInclusive;
    private final LocalDateTime intervalEndInclusive;
    private List<Booking> output;

    /**
     * @param intervalStartInclusive indicates the date and time when the search for the booking records starts
     * @param intervalEndInclusive indicates the date and time when the search for the booking records finishes
     */
    public GovernmentReport1Command(LocalDateTime intervalStartInclusive,
                                    LocalDateTime intervalEndInclusive){
        this.intervalStartInclusive = intervalStartInclusive;
        this.intervalEndInclusive = intervalEndInclusive;
        this.output = new ArrayList<Booking>();
    }

    /**
     * @param context object that provides access to global application state <p>
     * Verifies that: <p>
     *      * intervalStartInclusive is not equal to or after intervalEndInclusive <p>
     *      * currently logged-in user is a government representative <p>
     *      * the event of the Bookings is a TicketedEvent <p>
     *      * the status of the event of the Bookings is ACTIVE <p>
     *      * the sponsorship status of the event of the Bookings is ACCEPTED
     */
    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();

        if(user == null){
            logResult(LogStatus.GOVERNMENT_REPORT1_NOT_LOGGED_IN);
            return;
        }

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

                logResult(LogStatus.GOVERNMENT_REPORT1_SUCCESS);
            }
        } else {
            logResult( LogStatus.GOVERNMENT_REPORT1_USER_NOT_GOVERNMENT_REPRESENTATIVE);
            return;
        }

        if(output!=null) {
            Logger.getInstance().logAction("command.GovernmentReport1Command",
                    "Report_successfully_displayed");
        }else {
            Logger.getInstance().logAction("command.GovernmentReport1Command",
                    "report not displayed");
        }
    }

    /**
     * @return The requested booking records created by the command if successful and null otherwise
     */
    @Override
    public List<Booking> getResult() {
        return output;
    }
}
