package sobecki.michal.bankingplatform.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sobecki.michal.bankingplatform.entity.currencyaccount.CurrencyAccount;

import java.math.BigDecimal;

@Repository
public interface CurrencyAccountRepository extends CrudRepository<CurrencyAccount, String> {

    @Query(value = "SELECT DISTINCT c.amount FROM CurrencyAccount c WHERE c.currencyAccountId = ?1")
    BigDecimal getBalance(String currencyAccountId);
}
