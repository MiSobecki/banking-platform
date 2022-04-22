package sobecki.michal.bankingplatform.entity.platformincome;

import sobecki.michal.bankingplatform.entity.bankingoperation.BankingOperationType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class PlatformIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String platformIncomeId;

    @NotNull
    private String fromCurrencyAccountId;

    @NotNull
    private BankingOperationType bankingOperationType;

    private BigDecimal amount;

    private Date date;

    public void setFromCurrencyAccountId(String fromCurrencyAccountId) {
        this.fromCurrencyAccountId = fromCurrencyAccountId;
    }

    public void setBankingOperationType(BankingOperationType bankingOperationType) {
        this.bankingOperationType = bankingOperationType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
