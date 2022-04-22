package sobecki.michal.bankingplatform.entity.platformincome;

import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperationType;

import java.math.BigDecimal;

public interface PlatformIncomeFactory {
    PlatformIncome createPlatformIncome(String currencyAccountId,
                                        BankingOperationType bankingOperationType,
                                        BigDecimal amount);
}
