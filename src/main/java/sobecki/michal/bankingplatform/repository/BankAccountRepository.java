package sobecki.michal.bankingplatform.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sobecki.michal.bankingplatform.entity.BankAccount;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, String> {

}
