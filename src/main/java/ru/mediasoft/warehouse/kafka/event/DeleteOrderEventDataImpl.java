package ru.mediasoft.warehouse.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mediasoft.warehouse.kafka.Event;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrderEventDataImpl implements KafkaEvent {

    private Event event;
    private Long customerId;
    private UUID orderId;

    @Override
    public Event getEvent() {
        return Event.DELETE_ORDER;
    }
}
