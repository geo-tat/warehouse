package ru.mediasoft.warehouse.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final RestCurrencyProperties currencyProperties;
    private final RestAccountProperties accountProperties;
    private final RestCrmProperties crmProperties;

    @Bean
    public WebClient currencyWebClient() {
        return WebClient.builder()
                .baseUrl(currencyProperties.host)
                .build();
    }

    @Bean
    public WebClient accountWebClient() {
        return WebClient.builder()
                .baseUrl(accountProperties.host)
                .build();
    }

    @Bean
    public WebClient crmWebClient() {
        return WebClient.builder()
                .baseUrl(crmProperties.host)
                .build();
    }
}
