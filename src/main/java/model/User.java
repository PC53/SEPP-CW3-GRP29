package model;

public abstract class User extends Object {

    private final String email;
    private final String password;
    private final String paymentAccountEmail;

    protected User(String email, String password, String paymentAccountEmail) {
        this.email = email;
        // The password will need to be encrypted somehow.
        this.password = password;
        this.paymentAccountEmail = paymentAccountEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {

    }

    public boolean checkPasswordMatch(String password) {
        return false;
    }

    public void updatePassword(String newPassword) {

    }

    public String getPaymentAccountEmail() {
        return paymentAccountEmail;
    }

    public void setPaymentAccountEmail(String newPaymentAccountEmail) {

    }

    @Override
    public String toString() {
        return null;
    }
}
