package ru.mediasoft.warehouse.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Event {
    @JsonProperty("event")
    CREATE_ORDER,
    @JsonProperty("event")
    UPDATE_ORDER,
    @JsonProperty("event")
    DELETE_ORDER,
    @JsonProperty("event")
    UPDATE_ORDER_STATUS
}
