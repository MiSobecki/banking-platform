package sobecki.michal.bankingplatform.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperationType;
import sobecki.michal.bankingplatform.entity.currencyaccount.Currency;
import sobecki.michal.bankingplatform.entity.platformincome.PlatformIncome;

import java.math.BigDecimal;

@Repository
public interface PlatformIncomeRepository extends CrudRepository<PlatformIncome, String> {

    @Query(value = "SELECT count(p.amount) FROM PlatformIncome p")
    BigDecimal getPlatformIncome();

    @Query(value =
            "SELECT count(p.amount)" +
                    "FROM PlatformIncome p JOIN CurrencyAccount c " +
                    "WHERE p.bankingOperationType = ?1" +
                    "AND c.currency = ?2")
    BigDecimal getPlatformIncome(BankingOperationType type, Currency currency);
}
