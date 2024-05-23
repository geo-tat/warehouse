package ru.mediasoft.warehouse.product.service.currency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.mediasoft.warehouse.product.model.ExchangeRate;


import java.math.BigDecimal;
import java.util.Random;

@ConditionalOnProperty("currency-service.mock")
@Component
@Slf4j
@Primary
public class CurrencyServiceMockClient implements CurrencyServiceClient {
    @Override
    public ExchangeRate getCurrency() {
        log.info("Работает заглушка, генератор курса валют");
        ExchangeRate exchangeRateMock = new ExchangeRate();
        exchangeRateMock.setCNY(BigDecimal.valueOf(new Random().nextInt(200)));
        exchangeRateMock.setEUR(BigDecimal.valueOf(new Random().nextInt(200)));
        exchangeRateMock.setUSD(BigDecimal.valueOf(new Random().nextInt(200)));
        return exchangeRateMock;
    }
}
