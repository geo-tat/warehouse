package ru.mediasoft.warehouse.kafka.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        include = JsonTypeInfo.As.PROPERTY,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOrderEventDataImpl.class, name = "CREATE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderEventDataImpl.class, name = "UPDATE_ORDER"),
        @JsonSubTypes.Type(value = DeleteOrderEventDataImpl.class, name = "DELETE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderStatusEventDataImpl.class, name = "UPDATE_ORDER_STATUS"),
})
public interface KafkaEvent extends EventSource {
}
