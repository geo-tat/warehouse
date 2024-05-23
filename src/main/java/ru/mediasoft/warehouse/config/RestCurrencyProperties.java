package ru.mediasoft.warehouse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "rest.currency-service")
public class RestCurrencyProperties {

    String host;
    private Map<String, String> methods;

    public String getCurrenciesEndpoint() {
        return methods.get("get-currency");
    }

}
