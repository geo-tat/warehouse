package ru.mediasoft.warehouse.service.currency;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.mediasoft.warehouse.model.Currency;
import ru.mediasoft.warehouse.model.CurrencyType;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@SessionScope
@Component
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class CurrencyProvider {
    private final CurrencyClientService clientClient;
    private CurrencyType currencyType = CurrencyType.RUB;

    private final String FILEPATH = "src/main/resources/exchange-rate.json";


    public BigDecimal getCurrencyFromWeb() {
        Currency currency = clientClient.getCurrency();
        if (currency == null) {
            return getCurrencyFromFile();
        }
        return getCurrencyIndicator(currency);
    }

    private BigDecimal getCurrencyFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        Currency currency;
        try {
            currency = mapper.readValue(new File(FILEPATH), Currency.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Курс валюты берется из файла");
        return getCurrencyIndicator(currency);
    }

    private BigDecimal getCurrencyIndicator(Currency currency) {

        return switch (currencyType) {
            case USD -> currency.getUSD();
            case EUR -> currency.getEUR();
            case CNY -> currency.getCNY();
            default -> BigDecimal.valueOf(1);
        };
    }
}
