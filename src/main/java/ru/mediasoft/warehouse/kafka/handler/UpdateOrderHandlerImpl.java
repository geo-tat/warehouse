package ru.mediasoft.warehouse.kafka.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.mediasoft.warehouse.kafka.EventSource;
import ru.mediasoft.warehouse.kafka.EventStatus;
import ru.mediasoft.warehouse.kafka.event.UpdateOrderEventData;
import ru.mediasoft.warehouse.order.service.OrderService;

@RequiredArgsConstructor
@Component
public class UpdateOrderHandlerImpl implements EventHandler<UpdateOrderEventData> {
    private final OrderService service;

    @Override
    public Boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "Event must be not null");
        return EventStatus.UPDATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(UpdateOrderEventData eventStatus) {
        service.update(eventStatus.getCustomerId(), eventStatus.getOrderId(), eventStatus.getProducts());
        return eventStatus.getOrderId().toString();
    }
}
