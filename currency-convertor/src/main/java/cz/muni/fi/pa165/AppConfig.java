package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class AppConfig {

    @Bean(name = "exchangeRateTable")
    public ExchangeRateTable exchangeRateTable() {
        return new ExchangeRateTableImpl();
    }

    @Bean(name = "currencyConvertor")
    public CurrencyConvertor currencyConvertor() {
        return new CurrencyConvertorImpl(exchangeRateTable());
    }
}
