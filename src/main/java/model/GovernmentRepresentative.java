package model;

public class GovernmentRepresentative extends User{
    GovernmentRepresentative(String email, String password, String paymentAccountEmail){
        super(email, password, paymentAccountEmail);
    }

    @Override
    public String toString(){
        return null;
    }
}
