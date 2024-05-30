package ru.mediasoft.warehouse.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mediasoft.warehouse.kafka.handler.EventHandler;
import ru.mediasoft.warehouse.kafka.EventSource;

import java.util.HashSet;
import java.util.Set;


@Configuration
public class EventHandlerConfig {
    @Bean
    <T extends EventSource> Set<EventHandler<T>> eventHandlers(Set<EventHandler<T>> eventHandlers) {
        return new HashSet<>(eventHandlers);
    }
}
