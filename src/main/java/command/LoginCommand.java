package command;

import controller.Context;
import model.User;

import java.util.Map;

public class LoginCommand extends Object implements ICommand{
    private final String email;
    private final String password;
    private boolean result;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }
    @Override
    public void execute(Context context) {
        User user = context.getUserState().getAllUsers().get(email);
        if(user.checkPasswordMatch(password)){
            result = true;
            context.getUserState().setCurrentUser(user);
        }
    }

    @Override
    public Object getResult() {
        return result;
    }
}
