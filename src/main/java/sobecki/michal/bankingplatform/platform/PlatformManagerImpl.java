package sobecki.michal.bankingplatform.platform;

import org.springframework.stereotype.Service;
import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperationType;
import sobecki.michal.bankingplatform.entity.platformincome.PlatformIncome;
import sobecki.michal.bankingplatform.entity.platformincome.PlatformIncomeFactory;
import sobecki.michal.bankingplatform.service.CurrencyAccountService;
import sobecki.michal.bankingplatform.service.LackOfCashException;
import sobecki.michal.bankingplatform.service.NonPositivePaymentValue;
import sobecki.michal.bankingplatform.service.UnexistingBankingOperationType;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

@Service
public class PlatformManagerImpl implements PlatformManager {
    private final CurrencyAccountService currencyAccountService;
    private final PlatformIncomeFactory platformIncomeFactory;
    private final EntityManager entityManager;
    private BigDecimal commissionPercentage;

    public PlatformManagerImpl(CurrencyAccountService currencyAccountService,
                               PlatformIncomeFactory platformIncomeFactory, EntityManager entityManager) {
        this.currencyAccountService = currencyAccountService;
        this.platformIncomeFactory = platformIncomeFactory;
        this.entityManager = entityManager;
    }

    public BigDecimal performPayment(String currencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue {
        BigDecimal commission = amount.multiply(commissionPercentage);
        amount = amount.subtract(commission);

        BigDecimal balance = currencyAccountService.payment(currencyAccountId, amount);

        PlatformIncome platformIncome = platformIncomeFactory.createPlatformIncome(
                currencyAccountId,
                BankingOperationType.PAYMENT,
                commission);

        entityManager.persist(platformIncome);

        return balance;
    }

    public BigDecimal performWithdrawal(String currencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, LackOfCashException {
        BigDecimal commission = amount.multiply(commissionPercentage);
        amount = amount.subtract(commission);

        BigDecimal balance = currencyAccountService.withdrawal(currencyAccountId, amount);

        PlatformIncome platformIncome = platformIncomeFactory.createPlatformIncome(
                currencyAccountId,
                BankingOperationType.WITHDRAWAL,
                commission);

        entityManager.persist(platformIncome);

        return balance;
    }

    public BigDecimal performTransfer(String fromCurrencyAccountId, String toCurrencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, UnexistingBankingOperationType, LackOfCashException {
        BigDecimal commission = amount.multiply(commissionPercentage);
        amount = amount.subtract(commission);

        BigDecimal balance = currencyAccountService.transfer(fromCurrencyAccountId, toCurrencyAccountId, amount);

        PlatformIncome platformIncome = platformIncomeFactory.createPlatformIncome(
                fromCurrencyAccountId,
                BankingOperationType.TRANSFER,
                commission);

        entityManager.persist(platformIncome);

        return balance;
    }

    public BigDecimal performExchange(String fromCurrencyAccountId, String toCurrencyAccountId, BigDecimal amount)
            throws NonPositivePaymentValue, UnexistingBankingOperationType, LackOfCashException {
        BigDecimal commission = amount.multiply(commissionPercentage);
        amount = amount.subtract(commission);

        BigDecimal balance = currencyAccountService.exchange(fromCurrencyAccountId, toCurrencyAccountId, amount);

        PlatformIncome platformIncome = platformIncomeFactory.createPlatformIncome(
                fromCurrencyAccountId,
                BankingOperationType.EXCHANGE,
                commission);

        entityManager.persist(platformIncome);

        return balance;
    }

    public void setCommissionPercentage(BigDecimal commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }
}