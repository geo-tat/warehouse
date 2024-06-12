package ru.mediasoft.warehouse.currency;


import ru.mediasoft.warehouse.product.model.ExchangeRate;

public interface CurrencyServiceClient {

    ExchangeRate getCurrency();
}
