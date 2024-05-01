package ru.mediasoft.warehouse.search.enums;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum OperationType {
    @JsonProperty("operation")
    @JsonAlias({"="})
    EQUAL,

    @JsonProperty("operation")
    @JsonAlias({">="})
    GREATER_THAN_OR_EQ,

    @JsonProperty("operation")
    @JsonAlias({"<="})
    LESS_THAN_OR_EQ,

    @JsonProperty("operation")
    @JsonAlias({"~"})
    LIKE;
}
