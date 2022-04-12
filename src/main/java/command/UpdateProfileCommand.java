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

//      !check with user state class to see what edge cases are already handles!
        if (oldPassword.equals("") || newEmail.equals("") || oldPassword == null || newEmail == null) {
            successResult = false;
        } else if (currentUser.checkPasswordMatch(oldPassword) && currentUser.getEmail().equals(newEmail)){
            successResult = true;
        } else {
            successResult = false;
        }
        return successResult;
    }

    @Override
    public Object getResult() {
        if (successResult == true){
            return true;
        } else {
            return false;
        }
    }
}
