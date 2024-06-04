package ru.mediasoft.warehouse.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mediasoft.warehouse.kafka.Event;
import ru.mediasoft.warehouse.product.dto.ProductDtoForOrderIn;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderEventDataImpl implements KafkaEvent {

    private String deliveryAddress;
    private List<ProductDtoForOrderIn> products;
    private Long customerId;
    private Event event;

    @Override
    public Event getEvent() {
        return Event.CREATE_ORDER;
    }
}