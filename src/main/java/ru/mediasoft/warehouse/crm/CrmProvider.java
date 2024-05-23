package ru.mediasoft.warehouse.crm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
public class CrmProvider {
    private final CrmServiceClient crmServiceClient;

    public CompletableFuture<Map<String, String>> getInns(Set<String> logins) {
        return crmServiceClient.getUin(logins);
    }
}
