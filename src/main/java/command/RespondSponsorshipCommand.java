package command;

import controller.Context;
import model.GovernmentRepresentative;
import model.SponsorshipRequest;
import model.SponsorshipStatus;
import model.User;

public class RespondSponsorshipCommand extends Object implements ICommand{

    private final long requestNumber;
    private final int percentToSponsor;
    private boolean result;

    public RespondSponsorshipCommand(long requestNumber,
                                      int percentToSponsor){
        this.requestNumber = requestNumber;
        this.percentToSponsor = percentToSponsor;
    }

    @Override
    public void execute(Context context) {
        User user = context.getUserState().getCurrentUser();
        if(user instanceof GovernmentRepresentative){
            if((0<=percentToSponsor)&&(percentToSponsor<=100)){
                SponsorshipRequest sr = context.getSponsorshipState().findRequestByNumber(requestNumber);
                if(sr != null){
                    long eventNumber = sr.getEvent().getEventNumber();

                    if(sr.getStatus().equals(SponsorshipStatus.PENDING)){
                        if(percentToSponsor == 0) {
                            sr.reject();
                            // record in Entertainment provider system
                            sr.getEvent().getOrganiser().getProviderSystem().recordSponsorshipRejection(eventNumber);
                        }
                        else {
                            sr.accept(percentToSponsor, user.getPaymentAccountEmail());
                            // record in Entertainment provider system
                            sr.getEvent().getOrganiser().getProviderSystem()
                                    .recordSponsorshipAcceptance(eventNumber,percentToSponsor);

                            double amtToPay = (sr.getEvent().getNumTickets() * (float)percentToSponsor) / 100;
                            String sponsorEmail = sr.getSponsorAccountEmail();
                            String organiserEmail = sr.getEvent().getOrganiser().getPaymentAccountEmail();
                            if (context.getPaymentSystem().processPayment(sponsorEmail,organiserEmail,amtToPay)){
                                result = true;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Boolean getResult() {
        return result;
    }
}
