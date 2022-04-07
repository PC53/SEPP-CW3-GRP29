package command;

import controller.Context;
import model.Consumer;
import model.User;

import java.util.Map;

public class RegisterConsumerCommand extends Object implements ICommand{

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
        if (name != null
                && email != null
                && phone != null
                && password != null
                && paymentAccountEmail != null)
        {
            Map<String, User> users = context.getUserState().getAllUsers();
            for (String userEmail : users.keySet() ){
                if (userEmail.equals(email)) {
                    consumer = new Consumer(name,email,phone,password,paymentAccountEmail);
                    context.getUserState().addUser(consumer);
                    context.getUserState().setCurrentUser(consumer);
                    break;
                }
            }
        }

    }

    @Override
    public Consumer getResult() {
        return consumer;
    }
}
