package sobecki.michal.bankingplatform.service;

public class NonPositivePaymentValue extends Exception {
    public NonPositivePaymentValue(String errorMessage) {
        super(errorMessage);
    }
}
