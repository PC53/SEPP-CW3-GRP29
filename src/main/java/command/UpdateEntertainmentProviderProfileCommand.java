package command;

import controller.Context;
import logging.Logger;
import model.Consumer;
import model.EntertainmentProvider;
import model.User;
import state.IUserState;

import java.util.List;
import java.util.Map;

public class UpdateEntertainmentProviderProfileCommand extends UpdateProfileCommand{

    enum LogStatus{
        USER_UPDATE_PROFILE_SUCCESS,
        USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL,
                USER_UPDATE_PROFILE_NOT_ENTERTAINMENT_PROVIDER,
        USER_UPDATE_PROFILE_ORG_ALREADY_REGISTERED
    }

    private void logResult(UpdateEntertainmentProviderProfileCommand.LogStatus status){
        Logger.getInstance().logAction("command.UpdateEntertainmentProviderCommand",status);
    }

    private final String oldPassword;
    private final String newOrgName;
    private final String newOrgAddress;
    private final String newPaymentAccountEmail;
    private final String newMainRepName;
    private final String newMainRepEmail;
    private final String newPassword;
    private final List<String> newOtherRepNames;
    private final List<String> newOtherRepEmails;

    public UpdateEntertainmentProviderProfileCommand(String oldPassword,
                                                     String newOrgName,
                                                     String newOrgAddress,
                                                     String newPaymentAccountEmail,
                                                     String newMainRepName,
                                                     String newMainRepEmail,
                                                     String newPassword,
                                                     List<String> newOtherRepNames,
                                                     List<String> newOtherRepEmails){
        this.oldPassword = oldPassword;
        this.newOrgName = newOrgName;
        this.newOrgAddress = newOrgAddress;
        this.newPaymentAccountEmail = newPaymentAccountEmail;
        this.newMainRepName = newMainRepName;
        this.newMainRepEmail = newMainRepEmail;
        this.newPassword = newPassword;
        this.newOtherRepNames = newOtherRepNames;
        this.newOtherRepEmails = newOtherRepEmails;
    }

    @Override
    public void execute(Context context) {
        IUserState generalUserState = context.getUserState();
        Map<String, User> allUsers = generalUserState.getAllUsers();
        User currentUser = generalUserState.getCurrentUser();
        if(!(currentUser instanceof EntertainmentProvider)){
            logResult(LogStatus.USER_UPDATE_PROFILE_NOT_ENTERTAINMENT_PROVIDER);
        }

        if (oldPassword != null && newOrgName != null && newOrgAddress != null && newPaymentAccountEmail != null && newMainRepName != null
                && newMainRepEmail != null && newPassword != null && newOtherRepNames != null && newOtherRepEmails != null
                && currentUser != null && currentUser.checkPasswordMatch(oldPassword) && (currentUser instanceof EntertainmentProvider)) {
            boolean uniqueUserEmail = true;
            boolean uniqueOrgEmail = true;
            boolean uniqueOrgAddress = true;


            for (String userEmail : allUsers.keySet()) {
                if (userEmail.equals(newMainRepEmail) && !userEmail.equals(currentUser.getEmail())) {
                    uniqueUserEmail = false;
                    break;
                }
                for (String rep : newOtherRepEmails) {
                    if (userEmail.equals(rep)) {
                        uniqueUserEmail = false;
                        break;
                    }
                }
            }
            for (User user : allUsers.values()) {
                if (user instanceof EntertainmentProvider) {
                    if (newOrgName.equals(((EntertainmentProvider) user).getOrgName())
                            && !newOrgName.equals(((EntertainmentProvider) currentUser).getOrgName())) {
                        uniqueOrgEmail = false;
                        break;
                    }
                    if (newOrgAddress.equals(((EntertainmentProvider) user).getOrgAddress())
                            && !newOrgAddress.equals(((EntertainmentProvider) currentUser).getOrgAddress())) {
                        uniqueOrgAddress = false;
                        break;
                    }
                }
            }
            if (uniqueOrgAddress && uniqueOrgEmail && uniqueUserEmail) {
                ((EntertainmentProvider) currentUser).setOrgName(newOrgName);
                ((EntertainmentProvider) currentUser).setOrgAddress(newOrgAddress);
                ((EntertainmentProvider) currentUser).setPaymentAccountEmail(newPaymentAccountEmail);
                ((EntertainmentProvider) currentUser).setMainRepName(newMainRepName);
                ((EntertainmentProvider) currentUser).setMainRepEmail(newMainRepEmail);
                currentUser.updatePassword(newPassword);
                ((EntertainmentProvider) currentUser).setOtherRepNames(newOtherRepNames);
                ((EntertainmentProvider) currentUser).setOtherRepEmails(newOtherRepEmails);
                super.successResult = true;
                logResult(LogStatus.USER_UPDATE_PROFILE_SUCCESS);
            } else {
                super.successResult = false;
                logResult(LogStatus.USER_UPDATE_PROFILE_ORG_ALREADY_REGISTERED);
            }
        }else logResult(LogStatus.USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL);
    }
}
