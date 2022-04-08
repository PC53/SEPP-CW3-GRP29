package model;
import at.favre.lib.crypto.bcrypt.BCrypt;

public abstract class User extends Object {

    private String email;
    private String userPassword;
    private String paymentAccountEmail;

    protected User(String email, String password, String paymentAccountEmail) {
        this.email = email;
        // The password will need to be encrypted somehow.
        this.userPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        this.paymentAccountEmail = paymentAccountEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public boolean checkPasswordMatch(String password) {
        return BCrypt.verifyer().verify(password.toCharArray(), userPassword).verified;
    }

    public void updatePassword(String newPassword) {
        // This should be encrypted somehow.
        userPassword = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
    }

    public String getPaymentAccountEmail() {
        return paymentAccountEmail;
    }

    public void setPaymentAccountEmail(String newPaymentAccountEmail) {
        paymentAccountEmail = newPaymentAccountEmail;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", paymentAccountEmail='" + paymentAccountEmail + '\'' +
                '}';
    }
}
