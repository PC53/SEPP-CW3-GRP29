package command;

import controller.Context;
import model.EntertainmentProvider;
import model.Event;
import model.EventPerformance;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

public class AddEventPerformanceCommand extends Object implements ICommand{

    private final long eventNumber;
    private final LocalDateTime startDateTime;
    private final String venueAddress;
    private final LocalDateTime endDateTime;
    private final List<String> performerNames;
    private final boolean hasSocialDistancing;
    private final boolean hasAirFiltration;
    private final boolean isOutdoors;
    private final int capacityLimit;
    private final int venueSize;
    private EventPerformance finalEP;

    public AddEventPerformanceCommand(long eventNumber,
                                      String venueAddress,
                                      LocalDateTime startDateTime,
                                      LocalDateTime endDateTime,
                                      List<String> performerNames,
                                      boolean hasSocialDistancing,
                                      boolean hasAirFiltration,
                                      boolean isOutdoors,
                                      int capacityLimit,
                                      int venueSize){
        this.eventNumber = eventNumber;
        this.venueAddress = venueAddress;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.performerNames = performerNames;
        this.hasSocialDistancing = hasSocialDistancing;
        this.hasAirFiltration = hasAirFiltration;
        this.isOutdoors = isOutdoors;
        this.capacityLimit = capacityLimit;
        this.venueSize = venueSize;
    }
    
    @Override
    public void execute(Context context) {
        if(startDateTime.isBefore(endDateTime)
                && capacityLimit >= 1
                && venueSize >= 1){
            User currUser = context.getUserState().getCurrentUser();
            if(currUser instanceof EntertainmentProvider){
                Event event = context.getEventState().findEventByNumber(eventNumber);
                if(event != null){
                    if(currUser == event.getOrganiser()){
                        for(Event eve : context.getEventState().getAllEvents()){
                            if(eve.getTitle().equals(event.getTitle())){
                                // check if there is any clash
                                boolean clash = false;
                                for(EventPerformance ep : eve.getPerformances()){
                                    if(ep.getStartDateTime().isEqual(startDateTime)
                                            && ep.getEndDateTime().isEqual(endDateTime)){
                                        clash = true;
                                        break;
                                    }
                                }
                                if(!clash){
                                    finalEP = context.getEventState().createEventPerformance(event,
                                            venueAddress,
                                            startDateTime,
                                            endDateTime,
                                            performerNames,
                                            hasSocialDistancing,
                                            hasAirFiltration,
                                            isOutdoors,
                                            capacityLimit,
                                            venueSize);
                                    event.addPerformance(finalEP);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public EventPerformance getResult() {
        return finalEP;
    }
}
