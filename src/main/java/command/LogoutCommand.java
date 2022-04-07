package command;

import controller.Context;

public class LogoutCommand extends Object implements ICommand{

    public LogoutCommand(){

    }

    @Override
    public void execute(Context context) {
        if(context.getUserState().getCurrentUser() != null){
            context.getUserState().setCurrentUser(null);
        }
    }

    @Override
    public Object getResult() {
        return null;
    }
}
