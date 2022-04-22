package sobecki.michal.bankingplatform.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperation;
import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperationType;
import sobecki.michal.bankingplatform.entity.currencyaccount.Currency;

import java.sql.Date;
import java.util.List;

@Repository
public interface BankingOperationRepository extends CrudRepository<BankingOperation, String> {

    @Query(value =
            "SELECT b " +
                    "FROM BankingOperation b " +
                    "WHERE b.bankingOperationType = ?1" +
                    "AND b.currency = ?2" +
                    "AND b.date BETWEEN ?3 AND ?4")
    List<BankingOperation> getHistory(BankingOperationType bankingOperationType, Currency currency, Date from, Date to);

    @Query(value = "SELECT b FROM BankingOperation b WHERE b.fromCurrencyAccountId = ?1")
    List<BankingOperation> getBankingOperations(String currencyAccountId);
}
