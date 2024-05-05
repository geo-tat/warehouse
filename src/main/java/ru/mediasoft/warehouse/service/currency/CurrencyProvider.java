package ru.mediasoft.warehouse.service.currency;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.mediasoft.warehouse.model.CurrencyType;

@SessionScope
@Component
@Data
public class CurrencyProvider {
    private CurrencyType currency;

    public CurrencyProvider() {
        this.currency = CurrencyType.RUB;
    }
}