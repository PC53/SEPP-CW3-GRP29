package command;

import controller.Context;
import java.util.List;

public class RegisterEntertainmentProviderCommand extends Object implements ICommand{

    private final String orgName;
    private final String paymentAccountEmail;
    private final String orgAddress;
    private final String mainRepName;
    private final String mainRepEmail;
    private final String password;
    private final List<String> otherRepNames;
    private final List<String> otherRepEmails;

    public RegisterEntertainmentProviderCommand(String orgName,
                                                String orgAddress,
                                                String paymentAccountEmail,
                                                String mainRepName,
                                                String mainRepEmail,
                                                String password,
                                                List<String> otherRepNames,
                                                List<String> otherRepEmails){
        this.orgName=orgName;
        this.orgAddress=orgAddress;
        this.paymentAccountEmail = paymentAccountEmail;
        this.mainRepName = mainRepName;
        this.mainRepEmail = mainRepEmail;
        this.password = password;
        this.otherRepNames = otherRepNames;
        this.otherRepEmails = otherRepEmails;
    }

    @Override
    public void execute(Context context) {
        
    }

    @Override
    public Object getResult() {
        return null;
    }
}
