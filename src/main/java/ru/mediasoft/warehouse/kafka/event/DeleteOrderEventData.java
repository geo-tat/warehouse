package ru.mediasoft.warehouse.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mediasoft.warehouse.kafka.EventStatus;
import ru.mediasoft.warehouse.kafka.KafkaEvent;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrderEventData implements KafkaEvent {

    private EventStatus event;
    private Long customerId;
    private UUID orderId;

    @Override
    public EventStatus getEvent() {
        return EventStatus.DELETE_ORDER;
    }
}
