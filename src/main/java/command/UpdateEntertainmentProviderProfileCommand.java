package command;

import controller.Context;

import java.util.List;

public class UpdateEntertainmentProviderProfileCommand extends UpdateProfileCommand{

    private final String oldPassword;
    private final String newOrgName;
    private final String newOrgAddress;
    private final String newPaymentAccountEmail;
    private final String newMainRepName;
    private final String newMainRepEmail;
    private final String newPassword;
    private final List<String> newOtherRepNames;
    private final List<String> newOtherRepEmails;

    public UpdateEntertainmentProviderProfileCommand(String oldPassword,
                                                     String newOrgName,
                                                     String newOrgAddress,
                                                     String newPaymentAccountEmail,
                                                     String newMainRepName,
                                                     String newMainRepEmail,
                                                     String newPassword,
                                                     List<String> newOtherRepNames,
                                                     List<String> newOtherRepEmails){
        this.oldPassword = oldPassword;
        this.newOrgName = newOrgName;
        this.newOrgAddress = newOrgAddress;
        this.newPaymentAccountEmail = newPaymentAccountEmail;
        this.newMainRepName = newMainRepName;
        this.newMainRepEmail = newMainRepEmail;
        this.newPassword = newPassword;
        this.newOtherRepNames = newOtherRepNames;
        this.newOtherRepEmails = newOtherRepEmails;
    }

    @Override
    public void execute(Context context) {
//        super.execute(context);
    }
}
