package sobecki.michal.bankingplatform.platform;

import sobecki.michal.bankingplatform.service.LackOfCashException;
import sobecki.michal.bankingplatform.service.NonPositivePaymentValue;
import sobecki.michal.bankingplatform.service.UnexistingBankingOperationType;

import java.math.BigDecimal;

public interface PlatformManager {
    BigDecimal performPayment(String currencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue;

    BigDecimal performWithdrawal(String currencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, LackOfCashException;

    BigDecimal performTransfer(String fromCurrencyAccountId, String toCurrencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, UnexistingBankingOperationType, LackOfCashException;

    BigDecimal performExchange(String fromCurrencyAccountId, String toCurrencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, UnexistingBankingOperationType, LackOfCashException;

    void setCommissionPercentage(BigDecimal commissionPercentage);
}
