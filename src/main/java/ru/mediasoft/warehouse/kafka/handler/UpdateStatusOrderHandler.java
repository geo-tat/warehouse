package ru.mediasoft.warehouse.kafka.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.mediasoft.warehouse.kafka.EventSource;
import ru.mediasoft.warehouse.kafka.EventStatus;
import ru.mediasoft.warehouse.kafka.event.UpdateOrderStatusEventData;
import ru.mediasoft.warehouse.order.service.OrderService;

@RequiredArgsConstructor
@Component
public class UpdateStatusOrderHandler implements EventHandler<UpdateOrderStatusEventData> {
    private final OrderService service;

    @Override
    public Boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "Event must be not null");
        return EventStatus.CHANGE_ORDER_STATUS.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(UpdateOrderStatusEventData eventStatus) {
        service.changeOrderStatus(eventStatus.getOrderId(), eventStatus.getStatus());
        return "STATUS CHANGED";
    }
}
