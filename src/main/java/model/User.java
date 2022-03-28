package model;

public abstract class User extends Object {

    private String email;
    private String userPassword;
    private String paymentAccountEmail;

    protected User(String email, String password, String paymentAccountEmail) {
        this.email = email;
        // The password will need to be encrypted somehow.
        this.userPassword = password;
        this.paymentAccountEmail = paymentAccountEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public boolean checkPasswordMatch(String password) {
        return userPassword == password;
    }

    public void updatePassword(String newPassword) {
        // This should be encrypted somehow.
        userPassword = newPassword;
    }

    public String getPaymentAccountEmail() {
        return paymentAccountEmail;
    }

    public void setPaymentAccountEmail(String newPaymentAccountEmail) {
        paymentAccountEmail = newPaymentAccountEmail;
    }

    @Override
    public String toString() {
        return null;
    }
}
