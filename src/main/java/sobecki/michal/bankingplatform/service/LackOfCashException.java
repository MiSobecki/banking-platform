package sobecki.michal.bankingplatform.service;

public class LackOfCashException extends Exception {
    public LackOfCashException(String errorMessage) {
        super(errorMessage);
    }
}
