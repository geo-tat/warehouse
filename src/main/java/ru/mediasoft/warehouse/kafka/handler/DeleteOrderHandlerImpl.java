package ru.mediasoft.warehouse.kafka.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;
import ru.mediasoft.warehouse.kafka.event.EventSource;
import ru.mediasoft.warehouse.kafka.Event;
import ru.mediasoft.warehouse.kafka.event.DeleteOrderEventDataImpl;
import ru.mediasoft.warehouse.order.service.OrderService;

@Getter
@Setter
@RequiredArgsConstructor
public class DeleteOrderHandlerImpl implements EventHandler<DeleteOrderEventDataImpl> {
    private final OrderService service;


    @Override
    public Boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "Event must be not null");
        return Event.DELETE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(DeleteOrderEventDataImpl eventStatus) {
        service.deleteByOrderId(eventStatus.getCustomerId(), eventStatus.getOrderId());
        return "";
    }
}