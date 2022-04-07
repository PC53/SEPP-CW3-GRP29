package command;

import controller.Context;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

public class CancelEventCommand extends Object implements ICommand{

    private final String organiserMessage;
    private final long eventNumber;
    private boolean performanceResult = true;
    private boolean result = false;

    public CancelEventCommand(long eventNumber,
                              String organiserMessage){
        this.eventNumber = eventNumber;
        this.organiserMessage = organiserMessage;
    }
    
    @Override
    public void execute(Context context) {
        if(! organiserMessage.equals("")){
            User currUser = context.getUserState().getCurrentUser();
            if(currUser instanceof EntertainmentProvider){
                Event event = context.getEventState().findEventByNumber(eventNumber);
                if(event != null){
                    if(event.getStatus() == EventStatus.ACTIVE){
                        if(event.getOrganiser().getEmail().equals(currUser.getEmail())){

                            Collection<EventPerformance> performances = event.getPerformances();
                            LocalDateTime now = LocalDateTime.now();
                            for (EventPerformance performance : performances) {
                                if (now.isAfter(performance.getStartDateTime())
                                        && now.isBefore(performance.getEndDateTime())) {
                                    performanceResult = false;
                                    break;
                                }
                            }

                            if(performanceResult && event instanceof TicketedEvent){
                                if(((TicketedEvent) event).isSponsored()){
                                    int numTickets = ((TicketedEvent) event).getNumTickets();
                                    double discount = ((TicketedEvent) event).getOriginalTicketPrice()-((TicketedEvent)event).getDiscountedTicketPrice() ;
                                    String govEmail = ((TicketedEvent) event).getSponsorAccountEmail();
                                    String orgEmail = event.getOrganiser().getPaymentAccountEmail();
                                    if(context.getPaymentSystem().processRefund(govEmail,orgEmail,discount*numTickets)){
                                        result = true;
                                        event.cancel();
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Object getResult() {
        return result;
    }
}
