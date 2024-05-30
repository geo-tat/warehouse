package ru.mediasoft.warehouse.kafka.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.mediasoft.warehouse.kafka.EventSource;
import ru.mediasoft.warehouse.kafka.EventStatus;
import ru.mediasoft.warehouse.kafka.event.CreateOrderEventData;
import ru.mediasoft.warehouse.order.dto.OrderDtoIn;
import ru.mediasoft.warehouse.order.service.OrderService;

@RequiredArgsConstructor
@Component
public class CreateOrderHandlerImpl implements EventHandler<CreateOrderEventData> {
    private final OrderService service;

    @Override
    public Boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "Event must be not null");
        return EventStatus.CREATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(CreateOrderEventData eventStatus) {
        OrderDtoIn orderDtoIn = OrderDtoIn.builder()
                .deliveryAddress(eventStatus.getDeliveryAddress())
                .products(eventStatus.getProducts())
                .build();
        return service.create(eventStatus.getCustomerId(), orderDtoIn).toString();
    }
}