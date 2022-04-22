package sobecki.michal.bankingplatform.exchange;

import sobecki.michal.bankingplatform.entity.currencyaccount.Currency;

import java.math.BigDecimal;

public record ExchangeRate(Currency from, Currency to, BigDecimal rate) {
}
