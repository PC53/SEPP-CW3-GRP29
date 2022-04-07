package command;

import controller.Context;
import model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ListEventsOnGivenDateCommand extends ListEventsCommand implements ICommand{

    private final boolean userEventsOnly;
    private final boolean activeEventsOnly;
    private final LocalDateTime searchDateTime;
    private List<Event> events;

    public ListEventsOnGivenDateCommand(boolean userEventsOnly,
                                        boolean activeEventsOnly,
                                        LocalDateTime searchDateTime){
        super(userEventsOnly, activeEventsOnly);
        this.userEventsOnly = userEventsOnly;
        this.activeEventsOnly = activeEventsOnly;
        this.searchDateTime = searchDateTime;
    }

    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        List<Event> allEvents = context.getEventState().getAllEvents();

        if (userEventsOnly) {
            if (user instanceof EntertainmentProvider) {
                events = filterByEp(allEvents,(EntertainmentProvider)user);
            } else if (user instanceof Consumer){
                events = filterByPreference(allEvents,(Consumer) user);
            }
        }else events = allEvents;

        // for active events only
        if(activeEventsOnly){
            events = filterByActive(events);
        }

        events = events.stream().filter(f -> checkDateTimeOfPerformances(f,searchDateTime)).collect(Collectors.toList());

    }

    private boolean checkDateTimeOfPerformances(Event event,LocalDateTime searchDateTime){
        for(EventPerformance ep : event.getPerformances()){
            if(searchDateTime.minusDays(1).isBefore(ep.getStartDateTime())
                    && searchDateTime.plusDays(1).isAfter(ep.getEndDateTime())){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Event> getResult() {
        return null;
    }
}
