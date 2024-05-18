package ru.mediasoft.warehouse.service.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.mediasoft.warehouse.model.CurrencyType;
import ru.mediasoft.warehouse.model.ExchangeRate;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;


@Component
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class ExchangeRateProvider {
    private final CurrencyServiceClient clientClient;
    private final CurrencyProvider currencyProvider;

    private final String FILEPATH = "src/main/resources/exchange-rate.json";

    public BigDecimal getExchangeRate() {
        return Optional.ofNullable(getExchangeRateFromService())
                .orElseGet(() -> getExchangeRateFromFile());
    }

    private BigDecimal getExchangeRateFromService() {
        log.info("Получаем курс из второго сервиса или из кэша");
        return Optional.ofNullable(clientClient.getCurrency())
                .map(rate -> getExchangeRateByCurrency(rate)).orElse(null);
    }

    private BigDecimal getExchangeRateFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        ExchangeRate exchangeRate;
        try {
            exchangeRate = mapper.readValue(new File(FILEPATH), ExchangeRate.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Курс валюты берется из файла");
        return getExchangeRateByCurrency(exchangeRate);
    }

    private BigDecimal getExchangeRateByCurrency(ExchangeRate exchangeRate) {
        CurrencyType currencyType = currencyProvider.getCurrency();
        return switch (currencyType) {
            case USD -> exchangeRate.getUSD();
            case EUR -> exchangeRate.getEUR();
            case CNY -> exchangeRate.getCNY();
            default -> BigDecimal.ONE;
        };
    }
}
