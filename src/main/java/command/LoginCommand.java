package command;

import controller.Context;

public class LoginCommand extends Object implements ICommand{
    private final String email;
    private final String password;

    public LoginCommand(String email, String password) {
        this.email = email;
        this.password = password;
    }
    @Override
    public void execute(Context context) {

    }

    @Override
    public Object getResult() {
        return null;
    }
}
