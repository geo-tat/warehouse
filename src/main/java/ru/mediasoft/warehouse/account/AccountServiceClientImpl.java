package ru.mediasoft.warehouse.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.mediasoft.warehouse.config.RestAccountProperties;

import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AccountServiceClientImpl implements AccountServiceClient {
    private final WebClient accountWebClient;
    private final RestAccountProperties properties;

    public AccountServiceClientImpl(@Qualifier("accountWebClient") WebClient accountWebClient,
                                    RestAccountProperties properties) {
        this.accountWebClient = accountWebClient;
        this.properties = properties;
    }

    @Override
    public CompletableFuture<Map<String, String>> getAccount(Set<String> logins) {
        return accountWebClient
                .post()
                .uri(properties.getAccountEndpoint())
                .body(BodyInserters.fromValue(logins))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .toFuture();


    }
}
