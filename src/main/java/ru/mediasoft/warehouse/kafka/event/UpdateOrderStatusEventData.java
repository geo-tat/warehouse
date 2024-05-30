package ru.mediasoft.warehouse.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mediasoft.warehouse.kafka.EventStatus;
import ru.mediasoft.warehouse.kafka.KafkaEvent;
import ru.mediasoft.warehouse.order.model.OrderStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusEventData implements KafkaEvent {
    private EventStatus event;
    private UUID orderId;
    private OrderStatus status;

    @Override
    public EventStatus getEvent() {
        return EventStatus.CHANGE_ORDER_STATUS;
    }
}
