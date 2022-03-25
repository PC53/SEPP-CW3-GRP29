package external;

public class MockPaymentSystem implements PaymentSystem{

    public MockPaymentSystem() {

    }

    @Override
    public boolean processPayment(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount) {
        return false;
    }

    @Override
    public boolean processRefund(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount) {
        return false;
    }
}
