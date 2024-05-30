package ru.mediasoft.warehouse.kafka.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;
import ru.mediasoft.warehouse.kafka.EventSource;
import ru.mediasoft.warehouse.kafka.EventStatus;
import ru.mediasoft.warehouse.kafka.event.DeleteOrderEventData;
import ru.mediasoft.warehouse.order.service.OrderService;

@Getter
@Setter
@RequiredArgsConstructor
public class DeleteOrderHandlerImpl implements EventHandler<DeleteOrderEventData> {
    private final OrderService service;


    @Override
    public Boolean canHandle(EventSource eventSource) {
        Assert.notNull(eventSource, "Event must be not null");
        return EventStatus.DELETE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(DeleteOrderEventData eventStatus) {
        service.deleteByOrderId(eventStatus.getCustomerId(), eventStatus.getOrderId());
        return "";
    }
}