package ru.mediasoft.warehouse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "rest.account-service")
public class RestAccountProperties {
    String host;
    private Map<String, String> methods;

    public String getAccountEndpoint() {
        return methods.get("get-account");
    }

}
