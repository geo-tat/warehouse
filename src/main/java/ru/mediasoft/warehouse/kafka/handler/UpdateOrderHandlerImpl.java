package ru.mediasoft.warehouse.kafka.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.mediasoft.warehouse.kafka.event.EventSource;
import ru.mediasoft.warehouse.kafka.Event;
import ru.mediasoft.warehouse.kafka.event.UpdateOrderEventDataImpl;
import ru.mediasoft.warehouse.order.service.OrderService;

@RequiredArgsConstructor
@Component
public class UpdateOrderHandlerImpl implements EventHandler<UpdateOrderEventDataImpl> {
    private final OrderService service;

    @Override
    public Boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "Event must be not null");
        return Event.UPDATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(UpdateOrderEventDataImpl eventStatus) {
        service.update(eventStatus.getCustomerId(), eventStatus.getOrderId(), eventStatus.getProducts());
        return eventStatus.getOrderId().toString();
    }
}
