package command;

import controller.Context;

public class UpdateConsumerProfileCommand extends UpdateProfileCommand{

    private final String oldPassword;
    private final String newName;
    private final String newEmail;
    private final String newPhoneNumber;
    private final String newPassword;
    private final String newPaymentAccountEmail;
    private final ConsumerPreferences newPreferences;

    public UpdateConsumerProfileCommand(String oldPassword,
                                        String newEmail,
                                        String newName,
                                        String newPhoneNumber,
                                        String newPassword,
                                        String newPaymentAccountEmail,
                                        ConsumerPreferences newPreferences){
        this.oldPassword = oldPassword;
        this.newEmail = newEmail;
        this.newName = newName;
        this.newPhoneNumber = newPhoneNumber;
        this.newPassword = newPassword;
        this.newPaymentAccountEmail = newPaymentAccountEmail;
        this.newPreferences = newPreferences;
    }

    @Override
    public void execute(Context context) {

    }
}
