package sobecki.michal.bankingplatform.exchange;

import org.springframework.stereotype.Repository;
import sobecki.michal.bankingplatform.entity.currencyaccount.Currency;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class ExchangeRatesDb {

    private final Set<ExchangeRate> exchangeRates;

    public ExchangeRatesDb() {
        exchangeRates = Stream.of(
                new ExchangeRate(Currency.PLN, Currency.EUR, BigDecimal.valueOf(4.5))
        ).collect(Collectors.toSet());
    }

    public ExchangeRatesDb(Set<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public ExchangeRate getExchangeRate(Currency from, Currency to) {
        return exchangeRates.stream()
                .filter(exchangeRate -> exchangeRate.from() == from && exchangeRate.to() == to)
                .findFirst()
                .orElseThrow();
    }

}
