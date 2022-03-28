package external;

public class Transaction extends Object {
    private final String buyerAccountEmail;
    private final String sellerAccountEmail;
    private final double transactionAmount;


    Transaction(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount) {
        this.buyerAccountEmail = buyerAccountEmail;
        this.sellerAccountEmail = sellerAccountEmail;
        this.transactionAmount = transactionAmount;
    }

    public String getBuyerAccountEmail() {
        return buyerAccountEmail;
    }

    public String getSellerAccountEmail() {
        return sellerAccountEmail;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }
}
