package command;

import controller.Context;
import model.Consumer;

public class RegisterConsumerCommand extends Object implements ICommand{

    private final String name;
    private final String email;
    private final String phone;
    private final String password;
    private final String paymentAccountEmail;

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

    }

    @Override
    public Consumer getResult() {
        return null;
    }
}
