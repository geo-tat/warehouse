package ru.mediasoft.warehouse.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Event {
    @JsonProperty("CREATE_ORDER")
    CREATE_ORDER,
    @JsonProperty("UPDATE_ORDER")
    UPDATE_ORDER,
    @JsonProperty("DELETE_ORDER")
    DELETE_ORDER,
    @JsonProperty(" UPDATE_ORDER_STATUS")
    UPDATE_ORDER_STATUS
}
