package command;

import controller.Context;
import model.User;
import state.IUserState;
import state.UserState;

public abstract class UpdateProfileCommand extends Object implements ICommand{

    protected boolean successResult;

    public UpdateProfileCommand(){

    }

    protected boolean isProfileUpdateInvalid(Context context,
                                             String oldPassword,
                                             String newEmail) {
        IUserState generalUserState = context.getUserState();
        User currentUser = generalUserState.getCurrentUser();

        if (currentUser.checkPasswordMatch(oldPassword)) {
            boolean validEmail = true;
            for (String userEmail : generalUserState.getAllUsers().keySet()) {
                if (userEmail.equals(newEmail)) {
                    validEmail = false;
                    break;
                }
            }
            return validEmail;
        } else {
            return false;
        }
    }

    @Override
    public Object getResult() {
        return successResult == true;
    }
}
//merge test
