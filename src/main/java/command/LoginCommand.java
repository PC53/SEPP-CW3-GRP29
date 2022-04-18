package command;

import controller.Context;
import logging.Logger;
import model.User;

import java.util.Map;

public class LoginCommand extends Object implements ICommand{

    enum LogStatus{
        USER_LOGIN_SUCCESS,
        USER_LOGIN_EMAIL_NOT_REGISTERED,
        USER_LOGIN_WRONG_PASSWORD
    }

    private void logResult(LoginCommand.LogStatus status){
        Logger.getInstance().logAction("command.LoginCommand",status);
    }
    private final String email;
    private final String password;
    private User result;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }
    @Override
    public void execute(Context context) {
        User user = context.getUserState().getAllUsers().get(email);
        if(user != null) {
            if (user.checkPasswordMatch(password)) {
                result = user;
                context.getUserState().setCurrentUser(user);
                logResult(LogStatus.USER_LOGIN_SUCCESS);
            }else logResult(LogStatus.USER_LOGIN_WRONG_PASSWORD);
        }else logResult(LogStatus.USER_LOGIN_EMAIL_NOT_REGISTERED);
    }

    @Override
    public Object getResult() {
        return result;
    }
}
