package command;

import controller.Context;
import model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ListEventsCommand extends Object implements ICommand{

    private final boolean userEventsOnly;
    private final boolean activeEventsOnly;
    private List<Event> events;

    public ListEventsCommand(boolean userEventsOnly,
                             boolean activeEventsOnly){
        this.userEventsOnly = userEventsOnly;
        this.activeEventsOnly = activeEventsOnly;
    }

    @Override
    public void execute(Context context){
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

    }

    protected List<Event> filterByPreference(List<Event> allEvents,Consumer consumer) {
        return allEvents.stream()
                .filter(f -> checkPreferencesInPerformances(f,consumer.getPreferences()))
                .collect(Collectors.toList());
    }

    protected boolean checkPreferencesInPerformances(Event event, ConsumerPreferences preferences){
        for (EventPerformance ep : event.getPerformances()){
            if(ep.getStartDateTime().isAfter(LocalDateTime.now())
                    && ep.hasSocialDistancing() == preferences.preferSocialDistancing
                    && ep.hasAirFiltration() == preferences.preferAirFiltration
                    && ep.isOutdoors() == preferences.preferOutdoorsOnly
                    && ep.getVenueSize()  <= preferences.preferredMaxVenueSize
                    && ep.getCapacityLimit() <= preferences.preferredMaxCapacity
            ) return true;
        }
        return false;
    }

    protected List<Event> filterByEp(List<Event> events, EntertainmentProvider ep) {
        return events.stream().filter(f -> f.getOrganiser().equals(ep)).collect(Collectors.toList());
    }

    protected List<Event> filterByActive(List<Event> events) {
        return events.stream().filter(f -> f.getStatus() == EventStatus.ACTIVE).collect(Collectors.toList());
    }

    @Override
    public List<Event> getResult() {
        return events;
    }
}
