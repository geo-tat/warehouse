package ru.mediasoft.warehouse.service.currency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import ru.mediasoft.warehouse.model.Currency;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyClientServiceImpl implements CurrencyClientService {
    private final WebClient client;
    @Value("${currency-service.host}")
    private String host;

    @Value("${currency-service.methods.get-currency}")
    private String endpoint;

    @Cacheable(value = "currencyCache", key = "#root.methodName", unless = "#result == null")
    @Override
    public Currency getCurrency() {
        log.info("Курс валюты берется из стороннего микросервиса");
        return client.get()
                .uri(host + endpoint)
                .retrieve()
                .bodyToMono(Currency.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(3)))
                .onErrorResume(er -> Mono.empty())
                .block();
    }
}
