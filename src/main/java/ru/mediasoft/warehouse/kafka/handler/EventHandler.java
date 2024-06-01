package ru.mediasoft.warehouse.kafka.handler;


import ru.mediasoft.warehouse.kafka.event.EventSource;

public interface EventHandler<T extends EventSource> {

    Boolean canHandle(EventSource eventSource);

    String handleEvent(T eventStatus);
}