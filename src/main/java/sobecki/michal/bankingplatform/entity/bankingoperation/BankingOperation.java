package sobecki.michal.bankingplatform.entity.bankingoperation;

import sobecki.michal.bankingplatform.entity.currencyaccount.Currency;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class BankingOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String bankingOperationId;

    @NotNull
    private String fromCurrencyAccountId;

    @NotNull
    private BankingOperationType bankingOperationType;

    private String toCurrencyAccountId;

    private BigDecimal amount;

    private Currency currency;

    private Date date;

    public void setFromCurrencyAccountId(String fromCurrencyAccountId) {
        this.fromCurrencyAccountId = fromCurrencyAccountId;
    }

    public void setBankingOperationType(BankingOperationType bankingOperationType) {
        this.bankingOperationType = bankingOperationType;
    }

    public void setToCurrencyAccountId(String toCurrencyAccountId) {
        this.toCurrencyAccountId = toCurrencyAccountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
