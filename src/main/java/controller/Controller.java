package controller;

import command.ICommand;
import logging.Logger;
import state.IEventState;
import state.ISponsorshipState;
import state.IUserState;
import state.IBookingState;

public class Controller extends Object {

    private Context context;

    public Controller(){
        this.context = new Context();
    }

    public void runCommand(ICommand command){
        // run the command
        command.execute(context);
    }


}
