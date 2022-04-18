package command;

import controller.Context;
import logging.Logger;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListEventsCommand extends Object implements ICommand{

    enum LogStatus{
        LIST_USER_EVENTS_SUCCESS,
        LIST_USER_EVENTS_NOT_LOGGED_IN
    }

    private void logResult(ListEventsCommand.LogStatus status){
        Logger.getInstance().logAction("command.ListEventsCommand",status);
    }

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
        if(user ==null){
            logResult(LogStatus.LIST_USER_EVENTS_NOT_LOGGED_IN);
        }
        List<Event> allEvents = filterFutureEvents(context.getEventState().getAllEvents());

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

        logResult(LogStatus.LIST_USER_EVENTS_SUCCESS);

    }

    // filter out event with at least 1 EventPerformance which hasn't started
    private List<Event> filterFutureEvents(List<Event> allEvents){
        List<Event> output = new ArrayList<>();
        for(Event event : allEvents){
            for(EventPerformance performance: event.getPerformances()){
                if(!LocalDateTime.now().isAfter(performance.getStartDateTime())){
                    output.add(event);
                    break;
                }
            }
        }
        return output;
    }

    // filter events if they match user preferences
    protected List<Event> filterByPreference(List<Event> allEvents,Consumer consumer) {
        return allEvents.stream()
                .filter(f -> checkPreferencesInPerformances(f,consumer.getPreferences()))
                .collect(Collectors.toList());
    }

    // Event is selected if all the preferences match
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

    // return events by a particular Entertainment Provider
    protected List<Event> filterByEp(List<Event> events, EntertainmentProvider ep) {
        return events.stream().filter(f -> f.getOrganiser().equals(ep)).collect(Collectors.toList());
    }

    // return active events
    protected List<Event> filterByActive(List<Event> events) {
        List<Event> filteredEvents = events.stream().filter(f -> f.getStatus() == EventStatus.ACTIVE).collect(Collectors.toList());
        return filteredEvents;
    }

    @Override
    public List<Event> getResult() {
        return events;
    }
}
