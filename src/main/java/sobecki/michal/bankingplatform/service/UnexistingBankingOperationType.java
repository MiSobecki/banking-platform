package sobecki.michal.bankingplatform.service;

public class UnexistingBankingOperationType extends Exception {
    public UnexistingBankingOperationType(String errorMessage) {
        super(errorMessage);
    }
}
