package ru.mediasoft.warehouse.kafka;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.mediasoft.warehouse.kafka.event.CreateOrderEventData;
import ru.mediasoft.warehouse.kafka.event.DeleteOrderEventData;
import ru.mediasoft.warehouse.kafka.event.UpdateOrderEventData;
import ru.mediasoft.warehouse.kafka.event.UpdateOrderStatusEventData;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOrderEventData.class, name = "CREATE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderEventData.class, name = "UPDATE_ORDER"),
        @JsonSubTypes.Type(value = DeleteOrderEventData.class, name = "DELETE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderStatusEventData.class, name = "CHANGE_ORDER_STATUS"),
})
public interface KafkaEvent extends EventSource {
}
