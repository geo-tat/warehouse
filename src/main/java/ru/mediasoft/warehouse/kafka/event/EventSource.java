package ru.mediasoft.warehouse.kafka.event;

import ru.mediasoft.warehouse.kafka.Event;

public interface EventSource {
    Event getEvent();
}
