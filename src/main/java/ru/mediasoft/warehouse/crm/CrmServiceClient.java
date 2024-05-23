package ru.mediasoft.warehouse.crm;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface CrmServiceClient {
    CompletableFuture<Map<String, String>> getUin(Set<String> logins);
}
