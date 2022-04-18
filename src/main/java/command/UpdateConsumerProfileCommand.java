package command;

import controller.Context;
import logging.Logger;
import model.Consumer;
import model.ConsumerPreferences;
import model.User;
import state.IUserState;

import java.util.Map;

public class UpdateConsumerProfileCommand extends UpdateProfileCommand{

    enum LogStatus{
        USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL,
        USER_UPDATE_PROFILE_NOT_CONSUMER,
                USER_UPDATE_PROFILE_SUCCESS
    }

    private void logResult(UpdateConsumerProfileCommand.LogStatus status){
        Logger.getInstance().logAction("command.UpdateConsumerProfileCommand",status);
    }

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

        if (newName != null && !newName.equals("")
                && newEmail != null && !newEmail.equals("")
                && newPhoneNumber != null && !newPhoneNumber.equals("")
                && newPassword != null && !newPassword.equals("")
                && newPaymentAccountEmail != null && !newPaymentAccountEmail.equals("")) {
            if (currentUser.checkPasswordMatch(oldPassword) && (currentUser instanceof Consumer)) {
                boolean uniqueEmail = true;
                for (String userEmail : allUsers.keySet()) {
                    if (userEmail.equals(newEmail) && !(userEmail.equals(currentUser.getEmail()))) {
                        uniqueEmail = false;
                        break;
                    }
                }
                if (uniqueEmail) {
                    // need to delete element from hashmap and replace with this new 1
                    allUsers.remove(currentUser.getEmail());
                    Consumer temp = new Consumer(
                            newName,
                            newEmail,
                            newPhoneNumber,
                            newPassword,
                            newPaymentAccountEmail
                    );
                    temp.setPreferences(newPreferences);
                    allUsers.put(newEmail, temp);

                    super.successResult = true;
                    logResult(LogStatus.USER_UPDATE_PROFILE_SUCCESS);
                } else {
                    super.successResult = false;
                }
            } else logResult(LogStatus.USER_UPDATE_PROFILE_NOT_CONSUMER);
        }else logResult(LogStatus.USER_UPDATE_PROFILE_FIELDS_CANNOT_BE_NULL);
    }
}

//merge test
