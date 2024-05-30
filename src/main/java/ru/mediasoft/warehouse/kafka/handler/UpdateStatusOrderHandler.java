package ru.mediasoft.warehouse.kafka.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.mediasoft.warehouse.kafka.event.EventSource;
import ru.mediasoft.warehouse.kafka.Event;
import ru.mediasoft.warehouse.kafka.event.UpdateOrderStatusEventDataImpl;
import ru.mediasoft.warehouse.order.service.OrderService;

@RequiredArgsConstructor
@Component
public class UpdateStatusOrderHandler implements EventHandler<UpdateOrderStatusEventDataImpl> {
    private final OrderService service;

    @Override
    public Boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "Event must be not null");
        return Event.UPDATE_ORDER_STATUS.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(UpdateOrderStatusEventDataImpl eventStatus) {
        service.changeOrderStatus(eventStatus.getOrderId(), eventStatus.getStatus());
        return "STATUS CHANGED";
    }
}
