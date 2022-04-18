package command;

import controller.Context;
import logging.Logger;

public class LogoutCommand extends Object implements ICommand{

    enum LogStatus{
        USER_LOGOUT_SUCCESS,
        USER_LOGOUT_NOT_LOGGED_IN
    }

    private void logResult(LogoutCommand.LogStatus status){
        Logger.getInstance().logAction("command.LogoutCommand",status);
    }

    public LogoutCommand(){

    }

    @Override
    public void execute(Context context) {
        if(context.getUserState().getCurrentUser() != null){
            context.getUserState().setCurrentUser(null);
            logResult(LogStatus.USER_LOGOUT_SUCCESS);
        }else logResult(LogStatus.USER_LOGOUT_NOT_LOGGED_IN);
    }

    @Override
    public Object getResult() {
        return null;
    }
}
