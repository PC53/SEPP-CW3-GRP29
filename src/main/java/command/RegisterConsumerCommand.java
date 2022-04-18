package command;

import controller.Context;
import logging.Logger;
import model.Consumer;
import model.User;

import java.util.Map;

public class RegisterConsumerCommand extends Object implements ICommand{

    enum LogStatus{
        REGISTER_CONSUMER_SUCCESS,
        USER_REGISTER_FIELDS_CANNOT_BE_NULL,
                USER_REGISTER_EMAIL_ALREADY_REGISTERED,
        USER_LOGIN_SUCCESS
    }

    private void logResult(RegisterConsumerCommand.LogStatus status){
        Logger.getInstance().logAction("command.RegisterConsumerCommand",status);
    }

    private final String name;
    private final String email;
    private final String phone;
    private final String password;
    private final String paymentAccountEmail;
    private Consumer consumer;

    public RegisterConsumerCommand(String name,
                                   String email,
                                   String phoneNumber,
                                   String password,
                                   String paymentAccountEmail){
        this.name = name;
        this.email = email;
        this.phone = phoneNumber;
        this.password = password;
        this.paymentAccountEmail = paymentAccountEmail;
    }

    @Override
    public void execute(Context context) {
        if (name != null && !name.equals("")
                && email != null && !email.equals("")
                && phone != null && !phone.equals("")
                && password != null && !password.equals("")
                && paymentAccountEmail != null && !paymentAccountEmail.equals(""))
        {
            Map<String, User> users = context.getUserState().getAllUsers();

            if(users.get(email) == null){
                consumer = new Consumer(name,email,phone,password,paymentAccountEmail);
                context.getUserState().addUser(consumer);
                logResult(LogStatus.REGISTER_CONSUMER_SUCCESS);

                context.getUserState().setCurrentUser(consumer);
                logResult(LogStatus.USER_LOGIN_SUCCESS);

            }else logResult(LogStatus.USER_REGISTER_EMAIL_ALREADY_REGISTERED);
        }else logResult(LogStatus.USER_REGISTER_FIELDS_CANNOT_BE_NULL);

    }

    @Override
    public Consumer getResult() {
        return consumer;
    }
}
