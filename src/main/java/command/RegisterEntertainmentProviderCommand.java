package command;

import controller.Context;
import model.EntertainmentProvider;
import model.User;

import java.util.List;

public class RegisterEntertainmentProviderCommand extends Object implements ICommand{

    private final String orgName;
    private final String paymentAccountEmail;
    private final String orgAddress;
    private final String mainRepName;
    private final String mainRepEmail;
    private final String password;
    private final List<String> otherRepNames;
    private final List<String> otherRepEmails;
    private EntertainmentProvider newEP;

    public RegisterEntertainmentProviderCommand(String orgName,
                                                String orgAddress,
                                                String paymentAccountEmail,
                                                String mainRepName,
                                                String mainRepEmail,
                                                String password,
                                                List<String> otherRepNames,
                                                List<String> otherRepEmails){
        this.orgName=orgName;
        this.orgAddress=orgAddress;
        this.paymentAccountEmail = paymentAccountEmail;
        this.mainRepName = mainRepName;
        this.mainRepEmail = mainRepEmail;
        this.password = password;
        this.otherRepNames = otherRepNames;
        this.otherRepEmails = otherRepEmails;
    }

    @Override
    public void execute(Context context) {
        if(orgName!=null && orgAddress!=null && paymentAccountEmail!=null && mainRepEmail!=null
                && mainRepName != null && password != null && otherRepNames!=null && otherRepEmails != null){
            boolean repRegistered = false;
            for(String userEmail: context.getUserState().getAllUsers().keySet()){
                if(userEmail.equals(mainRepEmail)){
                    repRegistered = true;
                    break;
                }
            }
            boolean orgRegistered = false;
            if(!repRegistered){
                for(User user: context.getUserState().getAllUsers().values()){
                    if(user instanceof EntertainmentProvider){
                        if(orgName.equals(((EntertainmentProvider) user).getOrgName())) {
                            if (orgAddress.equals(((EntertainmentProvider) user).getOrgAddress())) {
                                orgRegistered = true;
                                break;
                            }
                        }
                    }
                }
            }
            if(!orgRegistered){
                newEP = new EntertainmentProvider(orgName,orgAddress,paymentAccountEmail,
                                                                        mainRepName,mainRepEmail,password,otherRepNames,
                                                                        otherRepEmails);
                context.getUserState().addUser(newEP);
                context.getUserState().setCurrentUser(newEP);
            }
        }
    }

    @Override
    public EntertainmentProvider getResult() {
        return newEP;
    }
}
