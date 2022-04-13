package command;

import controller.Context;
import model.Consumer;
import model.ConsumerPreferences;
import model.User;
import state.IUserState;

import java.util.Map;

public class UpdateConsumerProfileCommand extends UpdateProfileCommand{

    private final String oldPassword;
    private final String newName;
    private final String newEmail;
    private final String newPhoneNumber;
    private final String newPassword;
    private final String newPaymentAccountEmail;
    private final ConsumerPreferences newPreferences;

    public UpdateConsumerProfileCommand(String oldPassword,
                                        String newEmail,
                                        String newName,
                                        String newPhoneNumber,
                                        String newPassword,
                                        String newPaymentAccountEmail,
                                        ConsumerPreferences newPreferences){
        this.oldPassword = oldPassword;
        this.newEmail = newEmail;
        this.newName = newName;
        this.newPhoneNumber = newPhoneNumber;
        this.newPassword = newPassword;
        this.newPaymentAccountEmail = newPaymentAccountEmail;
        this.newPreferences = newPreferences;
    }

    @Override
    public void execute(Context context) {
        IUserState generalUserState = context.getUserState();
        Map<String, User> allUsers = generalUserState.getAllUsers();
        User currentUser = generalUserState.getCurrentUser();

        if (oldPassword != null && newName != null && newEmail != null && newPhoneNumber != null && newPassword != null && newPaymentAccountEmail != null
                && newPreferences != null && currentUser != null && currentUser.checkPasswordMatch(oldPassword) && (currentUser instanceof Consumer)) {
            boolean uniqueEmail = true;
            for (String userEmail : allUsers.keySet()) {
                if (userEmail.equals(newEmail)) {
                    uniqueEmail = false;
                    break;
                }
            }
            if (uniqueEmail) {
                ((Consumer) currentUser).setName(newName);
                ((Consumer) currentUser).setEmail(newEmail);
                ((Consumer) currentUser).setPhoneNumber(newPhoneNumber);
                ((Consumer) currentUser).updatePassword(newPassword);
                ((Consumer) currentUser).setPaymentAccountEmail(newPaymentAccountEmail);
                ((Consumer) currentUser).setPreferences(newPreferences);
                super.successResult = true;
            } else {
                super.successResult = false;
            }
        }
    }
}

//merge test
