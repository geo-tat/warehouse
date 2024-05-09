package ru.mediasoft.warehouse.exception;

import lombok.Getter;

@Getter
public class OrderStatusValidException extends RuntimeException {
    private final String s;

    public OrderStatusValidException(String s) {
        this.s = s;
    }
}
