package ru.mediasoft.warehouse.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@ConditionalOnProperty("rest.account-service.mock.enabled")
@Component
@Slf4j
@Primary
public class AccountServiceClientMock implements AccountServiceClient {

    @Override
    public CompletableFuture<Map<String, String>> getAccount(Set<String> logins) {
        log.info("Работает имитация account-service");
        Map<String, String> accounts = new HashMap<>();
        Random random = new Random();
        for (String login : logins) {
            accounts.put(login, String.valueOf(random.nextInt(9999)));
        }
        return CompletableFuture.supplyAsync(() -> accounts);

    }
}
