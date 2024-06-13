package ru.mediasoft.warehouse.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mediasoft.warehouse.kafka.Event;
import ru.mediasoft.warehouse.order.model.OrderStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusEventDataImpl implements KafkaEvent {
    private Event event;
    private UUID orderId;
    private OrderStatus status;

    @Override
    public Event getEvent() {
        return Event.UPDATE_ORDER_STATUS;
    }
}
