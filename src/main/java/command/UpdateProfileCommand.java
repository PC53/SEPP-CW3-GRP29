package command;

import controller.Context;
import logging.Logger;
import model.User;
import state.IUserState;
import state.UserState;

public abstract class UpdateProfileCommand extends Object implements ICommand{

    enum LogStatus{
        USER_UPDATE_PROFILE_NOT_LOGGED_IN,
        USER_UPDATE_PROFILE_WRONG_PASSWORD,
                USER_UPDATE_PROFILE_EMAIL_ALREADY_IN_USE
    }

    private void logResult(UpdateProfileCommand.LogStatus status){
        Logger.getInstance().logAction("command.UpdateProfileCommand",status);
    }

    protected boolean successResult;

    public UpdateProfileCommand(){

    }

    protected boolean isProfileUpdateInvalid(Context context,
                                             String oldPassword,
                                             String newEmail) {
        IUserState generalUserState = context.getUserState();
        User currentUser = generalUserState.getCurrentUser();

        if(currentUser == null){
            logResult(LogStatus.USER_UPDATE_PROFILE_NOT_LOGGED_IN);
        }

        if (currentUser.checkPasswordMatch(oldPassword)) {
            boolean validEmail = true;
            for (String userEmail : generalUserState.getAllUsers().keySet()) {
                if (userEmail.equals(newEmail)) {
                    logResult(LogStatus.USER_UPDATE_PROFILE_EMAIL_ALREADY_IN_USE);
                    validEmail = false;
                    break;
                }
            }
            return validEmail;
        } else {
            logResult(LogStatus.USER_UPDATE_PROFILE_WRONG_PASSWORD);
            return false;
        }
    }

    @Override
    public Object getResult() {
        return successResult == true;
    }
}
//merge test
