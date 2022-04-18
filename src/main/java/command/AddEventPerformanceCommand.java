package command;

import controller.Context;
import logging.Logger;
import model.EntertainmentProvider;
import model.Event;
import model.EventPerformance;
import model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AddEventPerformanceCommand extends Object implements ICommand{

    enum LogStatus{
        ADD_PERFORMANCE_SUCCESS,
        ADD_PERFORMANCE_START_AFTER_END,
        ADD_PERFORMANCE_CAPACITY_LESS_THAN_1,
        ADD_PERFORMANCE_VENUE_SIZE_LESS_THAN_1,
        ADD_PERFORMANCE_EVENTS_WITH_SAME_TITLE_CLASH,
        ADD_PERFORMANCE_USER_NOT_LOGGED_IN,
        ADD_PERFORMANCE_USER_NOT_ENTERTAINMENT_PROVIDER,
        ADD_PERFORMANCE_EVENT_NOT_FOUND,
        ADD_PERFORMANCE_USER_NOT_EVENT_ORGANISER
    }

    private void logResult(LogStatus status){
        Logger.getInstance().logAction("command.AddEventPerformance",status);
    }

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
        if(startDateTime.isBefore(endDateTime)){
            if(capacityLimit >=1){
                if  (venueSize >= 1) {
                    User currUser = context.getUserState().getCurrentUser();
                    if(currUser == null) {
                        logResult(LogStatus.ADD_PERFORMANCE_USER_NOT_LOGGED_IN);
                        return;
                    }

                    if (currUser instanceof EntertainmentProvider) {
                        Event event = context.getEventState().findEventByNumber(eventNumber);
                        if (event != null) {
                            if (currUser == event.getOrganiser()) {
                                for (Event eve : context.getEventState().getAllEvents()) {
                                    if (eve.getTitle().equals(event.getTitle())) {
                                        // check if there is any clash
                                        boolean clash = false;

                                        if (eve.getPerformances() != null) {
                                            for (EventPerformance ep : eve.getPerformances()) {
                                                if (ep.getStartDateTime().isEqual(startDateTime)
                                                        && ep.getEndDateTime().isEqual(endDateTime)) {
                                                    clash = true;
                                                    logResult(LogStatus.ADD_PERFORMANCE_EVENTS_WITH_SAME_TITLE_CLASH);
                                                    break;
                                                }
                                            }
                                        }

                                        if (!clash) {
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

                                            // record in Entertainment provider system
                                            ((EntertainmentProvider) currUser).getProviderSystem().recordNewPerformance(
                                                    eventNumber, finalEP.getPerformanceNumber(), startDateTime, endDateTime
                                            );
                                            logResult(LogStatus.ADD_PERFORMANCE_SUCCESS);
                                        }
                                    }
                                }
                            } else logResult(LogStatus.ADD_PERFORMANCE_USER_NOT_EVENT_ORGANISER);
                        }else logResult(LogStatus.ADD_PERFORMANCE_EVENT_NOT_FOUND);
                    }else logResult(LogStatus.ADD_PERFORMANCE_USER_NOT_ENTERTAINMENT_PROVIDER);
                }else logResult(LogStatus.ADD_PERFORMANCE_VENUE_SIZE_LESS_THAN_1);
            }else logResult(LogStatus.ADD_PERFORMANCE_CAPACITY_LESS_THAN_1);
        }else logResult(LogStatus.ADD_PERFORMANCE_START_AFTER_END);
    }

    @Override
    public EventPerformance getResult() {
        return finalEP;
    }
}
