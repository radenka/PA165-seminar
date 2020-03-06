package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency CZK = Currency.getInstance("CZK");

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CurrencyConvertor convertor = applicationContext.getBean(CurrencyConvertor.class);
        // dle Honzy
        // CurrencyConvertor convertor = (CurrencyConvertor) applicationContext.getBean("currencyConvertor");

        BigDecimal result = convertor.convert(EUR, CZK, BigDecimal.ONE);
        System.out.println(result);
    }
}


