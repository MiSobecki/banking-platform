package sobecki.michal.bankingplatform.entity.currencyaccount;

import org.hibernate.annotations.ColumnDefault;
import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class CurrencyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String currencyAccountId;

    @NotNull
    private String bankAccountId;

    @ColumnDefault("PLN")
    @NotNull
    private Currency currency;

    private BigDecimal amount;

    @OneToMany(mappedBy = "fromCurrencyAccountId")
    private List<BankingOperation> bankingOperations;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<BankingOperation> getBankingOperations() {
        return bankingOperations;
    }

    public String getCurrencyAccountId() {
        return currencyAccountId;
    }
}
