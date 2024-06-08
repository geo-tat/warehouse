package ru.mediasoft.warehouse.product.service.currency;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import ru.mediasoft.warehouse.config.RestCurrencyProperties;
import ru.mediasoft.warehouse.product.model.ExchangeRate;

import java.time.Duration;

@Slf4j

@Service
public class CurrencyServiceClientImpl implements CurrencyServiceClient {

    private final WebClient client;
    private final RestCurrencyProperties properties;

    public CurrencyServiceClientImpl(@Qualifier("currencyWebClient") WebClient client, RestCurrencyProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Cacheable(value = "currencyCache", key = "#root.methodName", unless = "#result == null")
    @Override
    public ExchangeRate getCurrency() {
        return client.get()
                .uri(properties.getCurrenciesEndpoint())
                .retrieve()
                .bodyToMono(ExchangeRate.class)
                .retryWhen(Retry.backoff(2, Duration.ofSeconds(3)))
                .onErrorResume(er -> Mono.empty())
                .block();
    }
}
