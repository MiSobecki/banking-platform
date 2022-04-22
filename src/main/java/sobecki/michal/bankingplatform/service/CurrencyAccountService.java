package sobecki.michal.bankingplatform.service;

import java.math.BigDecimal;

public interface CurrencyAccountService {
    BigDecimal payment(String currencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue;

    BigDecimal withdrawal(String currencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, LackOfCashException;

    BigDecimal transfer(String fromCurrencyAccountId, String toCurrencyAccountId, BigDecimal amount)
            throws LackOfCashException, NonPositivePaymentValue, UnexistingBankingOperationType;

    BigDecimal exchange(String fromCurrencyAccountId, String toCurrencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, LackOfCashException, UnexistingBankingOperationType;
}
