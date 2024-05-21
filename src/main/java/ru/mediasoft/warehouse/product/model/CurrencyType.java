package ru.mediasoft.warehouse.product.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public enum CurrencyType {
    @JsonAlias({"RUB"})
    RUB,
    @JsonAlias({"CNY"})
    CNY,
    @JsonAlias({"USD"})
    USD,
    @JsonAlias({"EUR"})
    EUR
}
