package ru.mediasoft.warehouse.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mediasoft.warehouse.kafka.EventStatus;
import ru.mediasoft.warehouse.kafka.KafkaEvent;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderEventData implements KafkaEvent {
    private EventStatus event;
    private Long customerId;
    private UUID orderId;
    private List<ProductDtoForOrderIn> products;

    @Override
    public EventStatus getEvent() {
        return EventStatus.UPDATE_ORDER;
    }
}
