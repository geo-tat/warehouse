package ru.mediasoft.warehouse.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
public class AccountProvider {
    private final AccountServiceClient accountServiceClient;

    public CompletableFuture<Map<String, String>> getAccount(Set<String> logins) {
        return accountServiceClient.getAccount(logins);
    }
}

