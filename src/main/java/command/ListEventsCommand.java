package command;

import controller.Context;
import model.EntertainmentProvider;
import model.Event;
import model.EventStatus;
import model.User;

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

        if(userEventsOnly){
            if(user instanceof EntertainmentProvider){
                events = filterByEP(allEvents,(EntertainmentProvider)user);
            }
        }
    }

    public List<Event> filterByEP(List<Event> events, EntertainmentProvider ep) {
        return events.stream().filter(f -> f.getOrganiser().equals(ep)).collect(Collectors.toList());
    }

    public List<Event> filterByActive(List<Event> events) {
        return events.stream().filter(f -> f.getStatus() == EventStatus.ACTIVE).collect(Collectors.toList());
    }

    @Override
    public Object getResult() {
        return null;
    }
}
