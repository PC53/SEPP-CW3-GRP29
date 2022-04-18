package command;

import controller.Context;
import logging.Logger;
import model.GovernmentRepresentative;
import model.SponsorshipRequest;
import model.SponsorshipStatus;
import model.User;

public class RespondSponsorshipCommand extends Object implements ICommand{

    enum LogStatus{
        RESPOND_SPONSORSHIP_APPROVE,
        RESPOND_SPONSORSHIP_REJECT,
                RESPOND_SPONSORSHIP_USER_NOT_LOGGED_IN,
        RESPOND_SPONSORSHIP_USER_NOT_GOVERNMENT_REPRESENTATIVE,
                RESPOND_SPONSORSHIP_REQUEST_NOT_FOUND,
        RESPOND_SPONSORSHIP_INVALID_PERCENTAGE,
                RESPOND_SPONSORSHIP_REQUEST_NOT_PENDING,
        RESPOND_SPONSORSHIP_PAYMENT_SUCCESS,
                RESPOND_SPONSORSHIP_PAYMENT_FAILED
    }

    private void logResult(RespondSponsorshipCommand.LogStatus status){
        Logger.getInstance().logAction("command.RespondSponsorshipCommand",status);
    }

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
        if(user == null){
            logResult(LogStatus.RESPOND_SPONSORSHIP_USER_NOT_LOGGED_IN);
        }
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
                            result = true;
                            logResult(LogStatus.RESPOND_SPONSORSHIP_REJECT);
                        }
                        else {
                            sr.accept(percentToSponsor, user.getPaymentAccountEmail());
                            // record in Entertainment provider system
                            sr.getEvent().getOrganiser().getProviderSystem()
                                    .recordSponsorshipAcceptance(eventNumber,percentToSponsor);
                            logResult(LogStatus.RESPOND_SPONSORSHIP_APPROVE);

                            double amtToPay = (sr.getEvent().getNumTickets() * (float)percentToSponsor) / 100;
                            String sponsorEmail = sr.getSponsorAccountEmail();
                            String organiserEmail = sr.getEvent().getOrganiser().getPaymentAccountEmail();
                            if (context.getPaymentSystem().processPayment(sponsorEmail,organiserEmail,amtToPay)){
                                result = true;
                                logResult(LogStatus.RESPOND_SPONSORSHIP_PAYMENT_SUCCESS);
                            }else logResult(LogStatus.RESPOND_SPONSORSHIP_PAYMENT_FAILED);
                        }
                    }else logResult(LogStatus.RESPOND_SPONSORSHIP_REQUEST_NOT_PENDING);
                }else logResult(LogStatus.RESPOND_SPONSORSHIP_REQUEST_NOT_FOUND);
            } else logResult(LogStatus.RESPOND_SPONSORSHIP_INVALID_PERCENTAGE);
        }else logResult(LogStatus.RESPOND_SPONSORSHIP_USER_NOT_GOVERNMENT_REPRESENTATIVE);
    }

    @Override
    public Boolean getResult() {
        return result;
    }
}
