package ru.mediasoft.warehouse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "rest.crm-service")
public class RestCrmProperties {
    String host;
    private Map<String, String> methods;

    public String getCrmEndpoint() {
        return methods.get("get-uin");
    }

}
