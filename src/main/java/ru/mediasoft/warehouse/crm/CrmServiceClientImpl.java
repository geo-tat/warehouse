package ru.mediasoft.warehouse.crm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.mediasoft.warehouse.config.RestCrmProperties;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CrmServiceClientImpl implements CrmServiceClient {
    private final WebClient crmWebClient;
    private final RestCrmProperties properties;

    CrmServiceClientImpl(@Qualifier("crmWebClient") WebClient crmWebClient,
                         RestCrmProperties properties) {
        this.crmWebClient = crmWebClient;
        this.properties = properties;
    }

    @Override
    public CompletableFuture<Map<String, String>> getUin(Set<String> logins) {
        return crmWebClient
                .post()
                .uri(properties.getCrmEndpoint())
                .body(BodyInserters.fromValue(logins))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .toFuture();
    }
}
