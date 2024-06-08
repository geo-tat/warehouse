package ru.mediasoft.warehouse.crm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@ConditionalOnProperty("rest.crm-service.mock.enabled")
@Component
@Slf4j
@Primary
public class CrmServiceClientMock implements CrmServiceClient {


    @Override
    public CompletableFuture<Map<String, String>> getUin(Set<String> logins) {
        log.info("Работает имитация crm-service");
        Map<String, String> uins = new HashMap<>();
        Random random = new Random();
        for (String login : logins) {
            uins.put(login, "0000000" + random.nextInt(999));
        }
        return CompletableFuture.supplyAsync(() -> uins);
    }
}
