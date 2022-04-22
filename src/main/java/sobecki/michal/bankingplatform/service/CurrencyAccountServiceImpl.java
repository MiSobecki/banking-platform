package sobecki.michal.bankingplatform.service;

import org.springframework.stereotype.Service;
import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperation;
import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperationFactory;
import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperationType;
import sobecki.michal.bankingplatform.entity.currencyaccount.CurrencyAccount;
import sobecki.michal.bankingplatform.exchange.ExchangeRate;
import sobecki.michal.bankingplatform.exchange.ExchangeRatesDb;
import sobecki.michal.bankingplatform.repository.CurrencyAccountRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;

@Service
public class CurrencyAccountServiceImpl implements CurrencyAccountService {

    private final CurrencyAccountRepository currencyAccountRepository;
    private final ExchangeRatesDb exchangeRatesDb;
    private final BankingOperationFactory bankingOperationFactory;

    public CurrencyAccountServiceImpl(CurrencyAccountRepository currencyAccountRepository,
                                      ExchangeRatesDb exchangeRatesDb,
                                      BankingOperationFactory bankingOperationFactory) {
        this.currencyAccountRepository = currencyAccountRepository;
        this.exchangeRatesDb = exchangeRatesDb;
        this.bankingOperationFactory = bankingOperationFactory;
    }

    public BigDecimal payment(String currencyAccountId, BigDecimal amount)
            throws NoSuchElementException, NonPositivePaymentValue {

        if (isAmountNonPositive(amount)) throw new NonPositivePaymentValue("Amount value should be > 0");

        CurrencyAccount currencyAccount = currencyAccountRepository.findById(currencyAccountId).orElseThrow();

        BigDecimal newAmount = currencyAccount.getAmount().add(amount);

        currencyAccount.setAmount(newAmount);

        BankingOperation bankingOperation = bankingOperationFactory.createPaymentOperation(
                currencyAccount.getCurrencyAccountId(), amount, currencyAccount.getCurrency());

        currencyAccount.getBankingOperations().add(bankingOperation);

        return newAmount;
    }

    public BigDecimal withdrawal(String currencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, LackOfCashException {

        if (isAmountNonPositive(amount)) throw new NonPositivePaymentValue("Amount value should be > 0");

        CurrencyAccount currencyAccount = currencyAccountRepository.findById(currencyAccountId).orElseThrow();

        BigDecimal newAmount = currencyAccount.getAmount().subtract(amount);

        if (newAmount.compareTo(BigDecimal.ZERO) <= 0)
            throw new LackOfCashException("There isn't enough cash on the account to withdraw it");

        currencyAccount.setAmount(newAmount);

        BankingOperation bankingOperation = bankingOperationFactory.createWithdrawalOperation(
                currencyAccount.getCurrencyAccountId(), amount, currencyAccount.getCurrency());

        currencyAccount.getBankingOperations().add(bankingOperation);

        return newAmount;
    }

    public BigDecimal transfer(String fromCurrencyAccountId, String toCurrencyAccountId, BigDecimal amount)
            throws LackOfCashException, NonPositivePaymentValue, UnexistingBankingOperationType {

        if (isAmountNonPositive(amount)) throw new NonPositivePaymentValue("Amount value should be > 0");

        CurrencyAccount fromCurrencyAccount = currencyAccountRepository.findById(fromCurrencyAccountId).orElseThrow();
        CurrencyAccount toCurrencyAccount = currencyAccountRepository.findById(toCurrencyAccountId).orElseThrow();

        return transferMoneyBetweenAccounts(fromCurrencyAccount, toCurrencyAccount,
                amount, amount, BankingOperationType.TRANSFER);
    }

    public BigDecimal exchange(String fromCurrencyAccountId, String toCurrencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, LackOfCashException, UnexistingBankingOperationType {

        if (isAmountNonPositive(amount)) throw new NonPositivePaymentValue("Amount value should be > 0");

        CurrencyAccount fromCurrencyAccount = currencyAccountRepository.findById(fromCurrencyAccountId).orElseThrow();
        CurrencyAccount toCurrencyAccount = currencyAccountRepository.findById(toCurrencyAccountId).orElseThrow();

        ExchangeRate exchangeRate = exchangeRatesDb.getExchangeRate(
                fromCurrencyAccount.getCurrency(),
                toCurrencyAccount.getCurrency());

        BigDecimal newToAmount = toCurrencyAccount
                .getAmount()
                .add(amount.divide(exchangeRate.rate(), RoundingMode.DOWN));

        return transferMoneyBetweenAccounts(fromCurrencyAccount, toCurrencyAccount,
                amount, newToAmount, BankingOperationType.EXCHANGE);
    }

    private boolean isAmountNonPositive(BigDecimal amount) {
        return (amount.compareTo(BigDecimal.ZERO) <= 0);
    }

    private BigDecimal transferMoneyBetweenAccounts(CurrencyAccount fromCurrencyAccount,
                                                    CurrencyAccount toCurrencyAccount,
                                                    BigDecimal fromAmount,
                                                    BigDecimal toAmount,
                                                    BankingOperationType bankingOperationType)
            throws LackOfCashException, UnexistingBankingOperationType {

        BigDecimal newFromAmount = fromCurrencyAccount.getAmount().subtract(fromAmount);

        if (newFromAmount.compareTo(BigDecimal.ZERO) <= 0)
            throw new LackOfCashException("There isn't enough cash on the account to withdraw it");

        fromCurrencyAccount.setAmount(newFromAmount);
        toCurrencyAccount.setAmount(toCurrencyAccount.getAmount().add(toAmount));

        BankingOperation bankingOperation = switch (bankingOperationType) {
            case TRANSFER -> bankingOperationFactory.createTransferOperation(
                    fromCurrencyAccount.getCurrencyAccountId(),
                    toCurrencyAccount.getCurrencyAccountId(),
                    fromAmount,
                    fromCurrencyAccount.getCurrency());
            case EXCHANGE -> bankingOperationFactory.createExchangeOperation(
                    fromCurrencyAccount.getCurrencyAccountId(),
                    toCurrencyAccount.getCurrencyAccountId(),
                    fromAmount,
                    fromCurrencyAccount.getCurrency());
            default -> throw new UnexistingBankingOperationType("There is no such operation type");
        };

        fromCurrencyAccount.getBankingOperations().add(bankingOperation);

        return newFromAmount;
    }

}
