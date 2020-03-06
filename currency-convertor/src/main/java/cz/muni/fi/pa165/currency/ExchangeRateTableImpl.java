package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Currency;

@Named
public class ExchangeRateTableImpl implements ExchangeRateTable {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency CZK = Currency.getInstance("CZK");

    public static final BigDecimal EUR_CZK_RATE = BigDecimal.valueOf(27);

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
        // Apache Commons Lang library: Validate.isNull(sourceCurrency) // Commons Lang must be added to dependencies

        if (sourceCurrency == null || targetCurrency == null) {
            throw new IllegalArgumentException("Source or target is null.");
        }

        if (sourceCurrency.equals(EUR) && targetCurrency.equals(CZK)) {
            return EUR_CZK_RATE;
        }
        return null;
    }
}
