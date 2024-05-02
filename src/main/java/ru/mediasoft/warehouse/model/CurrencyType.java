package ru.mediasoft.warehouse.model;

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
