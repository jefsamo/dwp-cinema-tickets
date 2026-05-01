package thirdparty.paymentgateway;

public class TicketPaymentServiceImpl implements TicketPaymentService {

    @Override
    public void makePayment(long accountId, int totalAmountToPay) {
        // Real implementation omitted, assume working code will take the payment using a card pre linked to the account.
        validateAccountId(accountId);
        validateAmount(totalAmountToPay);
    }

    private void validateAccountId(long accountId) {
        if (accountId <= 0) {
            throw new IllegalArgumentException("Invalid account id.");
        }
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive.");
        }
    }
}
