package sobecki.michal.bankingplatform.entity.bankingoperation;

import org.springframework.stereotype.Service;
import sobecki.michal.bankingplatform.entity.currencyaccount.Currency;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class BankingOperationFactoryImpl implements BankingOperationFactory {

    public BankingOperation createPaymentOperation(String currencyAccountId, BigDecimal amount, Currency currency) {
        return oneAccountOperation(currencyAccountId, amount, BankingOperationType.PAYMENT, currency);
    }

    public BankingOperation createWithdrawalOperation(String currencyAccountId, BigDecimal amount, Currency currency) {
        return oneAccountOperation(currencyAccountId, amount, BankingOperationType.WITHDRAWAL, currency);
    }

    public BankingOperation createTransferOperation(String fromCurrencyAccountId,
                                                    String toCurrencyAccountId,
                                                    BigDecimal amount,
                                                    Currency currency) {
        return twoAccountsOperation(fromCurrencyAccountId, toCurrencyAccountId,
                amount, BankingOperationType.TRANSFER, currency);
    }

    public BankingOperation createExchangeOperation(String fromCurrencyAccountId,
                                                    String toCurrencyAccountId,
                                                    BigDecimal amount,
                                                    Currency currency) {
        return twoAccountsOperation(fromCurrencyAccountId, toCurrencyAccountId,
                amount, BankingOperationType.EXCHANGE, currency);
    }

    private BankingOperation oneAccountOperation(String currencyAccountId,
                                                 BigDecimal amount,
                                                 BankingOperationType type,
                                                 Currency currency) {
        BankingOperation bankingOperation = new BankingOperation();

        bankingOperation.setBankingOperationType(type);
        bankingOperation.setAmount(amount);
        bankingOperation.setDate(Date.valueOf(LocalDate.now()));
        bankingOperation.setFromCurrencyAccountId(currencyAccountId);
        bankingOperation.setCurrency(currency);

        return bankingOperation;
    }

    private BankingOperation twoAccountsOperation(String fromCurrencyAccountId,
                                                  String toCurrencyAccountId,
                                                  BigDecimal amount,
                                                  BankingOperationType type,
                                                  Currency currency) {
        BankingOperation bankingOperation = oneAccountOperation(fromCurrencyAccountId, amount, type, currency);
        bankingOperation.setToCurrencyAccountId(toCurrencyAccountId);

        return bankingOperation;
    }
}
