package ru.mediasoft.warehouse.kafka;

public interface EventSource {
    EventStatus getEvent();
}
