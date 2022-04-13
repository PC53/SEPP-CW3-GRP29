package external;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MockPaymentSystem extends Object implements PaymentSystem{

    private List<Transaction> transactions;

    public MockPaymentSystem() {
        this.transactions = new ArrayList<>();
    }

    @Override
    public boolean processPayment(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount) {
        Transaction instance = new Transaction(buyerAccountEmail,sellerAccountEmail,transactionAmount);
        transactions.add(instance);
        return true;
        // Can be fail but do not know how to implement it
    }

    @Override
    public boolean processRefund(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount) {
        for (int i = 0; i < transactions.size(); i++) {
            if (Objects.equals(transactions.get(i).getBuyerAccountEmail(), buyerAccountEmail)) {
                if (Objects.equals(transactions.get(i).getSellerAccountEmail(), sellerAccountEmail)) {
                    if (Objects.equals(transactions.get(i).getTransactionAmount(), transactionAmount)) {
                        transactions.remove(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
