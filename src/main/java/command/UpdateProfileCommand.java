package command;

import controller.Context;

public abstract class UpdateProfileCommand extends Object implements ICommand{

    protected boolean successResult;

    public UpdateProfileCommand(){

    }

    protected boolean isProfileUpdateInvalid(Context context,
                                             String oldPassword,
                                             String newEmail){
        return successResult;
    }

    @Override
    public Object getResult() {
        return null;
    }
}
