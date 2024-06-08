package ru.mediasoft.warehouse.account;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface AccountServiceClient {
    CompletableFuture<Map<String, String>> getAccount(Set<String> logins);
}
