package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    private static Currency CZK = Currency.getInstance("CZK");
    private static Currency EUR = Currency.getInstance("EUR");

    @Mock
    private ExchangeRateTable exchangeRateTable;
    private CurrencyConvertor currencyConvertor;

    @Before
    public void init() {
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Rule
    // dle tutorialu
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenReturn(new BigDecimal("10"));

        assertEquals(new BigDecimal("20.00"), currencyConvertor.convert(EUR, CZK, new BigDecimal("2.00050")));
        assertEquals(new BigDecimal("20.01"), currencyConvertor.convert(EUR, CZK, new BigDecimal("2.00051")));
        assertEquals(new BigDecimal("20.01"), currencyConvertor.convert(EUR, CZK, new BigDecimal("2.00149")));
        assertEquals(new BigDecimal("20.02"), currencyConvertor.convert(EUR, CZK, new BigDecimal("2.00150")));
    }


    @Test
    public void testConvertWithNullSourceCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(null, CZK, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(EUR, null, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        expectedException.expect(IllegalArgumentException.class);
        currencyConvertor.convert(EUR, CZK, null);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenReturn(null);
        expectedException.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(EUR, CZK, BigDecimal.ONE);
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(EUR, CZK))
                .thenThrow(ExternalServiceFailureException.class);
        expectedException.expect(UnknownExchangeRateException.class);
        currencyConvertor.convert(EUR, CZK, BigDecimal.ONE);
    }

}
