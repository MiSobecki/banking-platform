package sobecki.michal.bankingplatform.entity.bankingoperation;

import sobecki.michal.bankingplatform.entity.currencyaccount.Currency;

import java.math.BigDecimal;

public interface BankingOperationFactory {
    BankingOperation createPaymentOperation(String currencyAccountId, BigDecimal amount, Currency currency);

    BankingOperation createWithdrawalOperation(String currencyAccountId, BigDecimal amount, Currency currency);

    BankingOperation createTransferOperation(String fromCurrencyAccountId,
                                             String toCurrencyAccountId,
                                             BigDecimal amount,
                                             Currency currency);

    BankingOperation createExchangeOperation(String fromCurrencyAccountId,
                                             String toCurrencyAccountId,
                                             BigDecimal amount,
                                             Currency currency);
}
