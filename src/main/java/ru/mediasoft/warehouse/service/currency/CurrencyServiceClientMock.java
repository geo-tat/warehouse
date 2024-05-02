package ru.mediasoft.warehouse.service.currency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.mediasoft.warehouse.model.Currency;

import java.math.BigDecimal;
import java.util.Random;

@ConditionalOnProperty("currency-service.mock")
@Component
@Slf4j
@Primary
public class CurrencyServiceClientMock implements CurrencyClientService {
    @Override
    public Currency getCurrency() {
        log.info("Работает заглушка, генератор курса валют");
        Currency currencyMock = new Currency();
        currencyMock.setCNY(BigDecimal.valueOf(new Random().nextInt(200)));
        currencyMock.setEUR(BigDecimal.valueOf(new Random().nextInt(200)));
        currencyMock.setUSD(BigDecimal.valueOf(new Random().nextInt(200)));
        return currencyMock;
    }
}
