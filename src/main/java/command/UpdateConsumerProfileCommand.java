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
            } else {
                super.successResult = false;
            }
        }
    }
}

//merge test
