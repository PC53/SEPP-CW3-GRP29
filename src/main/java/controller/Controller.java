package controller;

import command.ICommand;
import logging.Logger;
import state.IEventState;
import state.ISponsorshipState;
import state.IUserState;
import state.IBookingState;

public class Controller extends Object {

    /* private PaymentSystem paymentSystem;
    private IUserState userState;
    private IEventState eventState;
    private IBookingState bookingState;

    private ISponsorshipState sponsorshipState;

    private ISponsorshipState attribute; */

    private Context context;

    public Controller(){
        this.context = new Context();
    }

    public void runCommand(ICommand command){
        // run the command
        command.execute(context);

        Object result = command.getResult();

        // log the command

        //String commandName = command.getName();

        //Logger.getInstance().logAction(commandName,command.getResult());
    }


}
