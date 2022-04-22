package sobecki.michal.bankingplatform.entity.platformincome;

import org.springframework.stereotype.Service;
import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperationType;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@Service
public class PlatformIncomeFactoryImpl implements PlatformIncomeFactory {

    @Override
    public PlatformIncome createPlatformIncome(String currencyAccountId,
                                               BankingOperationType bankingOperationType,
                                               BigDecimal amount) {

        PlatformIncome platformIncome = new PlatformIncome();
        platformIncome.setFromCurrencyAccountId(currencyAccountId);
        platformIncome.setBankingOperationType(bankingOperationType);
        platformIncome.setAmount(amount);
        platformIncome.setDate(Date.valueOf(LocalDate.now()));

        return platformIncome;
    }
}
