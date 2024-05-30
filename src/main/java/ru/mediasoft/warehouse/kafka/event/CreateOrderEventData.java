package ru.mediasoft.warehouse.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mediasoft.warehouse.kafka.EventStatus;
import ru.mediasoft.warehouse.kafka.KafkaEvent;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderEventData implements KafkaEvent {

    private String deliveryAddress;
    private List<ProductDtoForOrderIn> products;
    private Long customerId;
    private EventStatus event;

    @Override
    public EventStatus getEvent() {
        return EventStatus.CREATE_ORDER;
    }
}