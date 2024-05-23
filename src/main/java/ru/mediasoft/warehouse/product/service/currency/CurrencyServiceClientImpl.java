package ru.mediasoft.warehouse.product.service.currency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import ru.mediasoft.warehouse.config.RestProperties;
import ru.mediasoft.warehouse.product.model.ExchangeRate;


import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrencyServiceClientImpl implements CurrencyServiceClient {
    private final WebClient client;
    private final RestProperties properties;

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
