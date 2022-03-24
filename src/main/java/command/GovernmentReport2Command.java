package command;

import controller.Context;

public class GovernmentReport2Command extends Object implements ICommand{

    private final String orgName;

    public GovernmentReport2Command(String orgName){
        this.orgName=orgName;
    }

    @Override
    public void execute(Context context) {

    }

    @Override
    public Object getResult() {
        return null;
    }
}
