package ru.mediasoft.warehouse.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mediasoft.warehouse.kafka.Event;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderEventDataImpl implements KafkaEvent {
    private Event event;
    private Long customerId;
    private UUID orderId;
    private List<ProductDtoForOrderIn> products;

    @Override
    public Event getEvent() {
        return Event.UPDATE_ORDER;
    }
}
