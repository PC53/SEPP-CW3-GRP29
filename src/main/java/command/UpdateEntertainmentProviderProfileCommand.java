package command;

import controller.Context;
import model.Consumer;
import model.EntertainmentProvider;
import model.User;
import state.IUserState;

import java.util.List;
import java.util.Map;

public class UpdateEntertainmentProviderProfileCommand extends UpdateProfileCommand{

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
            } else {
                super.successResult = false;
            }
        }
    }
}
