package sobecki.michal.bankingplatform.entity;

import sobecki.michal.bankingplatform.entity.currencyaccount.CurrencyAccount;

import javax.persistence.*;
import java.util.List;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String bankAccountId;

    @OneToMany(mappedBy = "bankAccountId")
    private List<CurrencyAccount> currencyAccounts;
}
