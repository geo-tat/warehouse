package ru.mediasoft.warehouse.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final RestProperties properties;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(properties.host)
                .build();
    }
}
