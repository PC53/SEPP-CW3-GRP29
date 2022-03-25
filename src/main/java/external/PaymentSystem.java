package external;

public interface PaymentSystem {

    /**
     *
     * @param buyerAccountEmail
     * @param sellerAccountEmail
     * @param transactionAmount
     * @return
     */
    boolean	processPayment(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount);

    /**
     *
     * @param buyerAccountEmail
     * @param sellerAccountEmail
     * @param transactionAmount
     * @return
     */
    boolean	processRefund(String buyerAccountEmail, String sellerAccountEmail, double transactionAmount);

}
