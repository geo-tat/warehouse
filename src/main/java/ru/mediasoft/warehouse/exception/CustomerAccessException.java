package ru.mediasoft.warehouse.exception;

import lombok.Getter;

@Getter
public class CustomerAccessException extends RuntimeException {
    private final String message;

    public CustomerAccessException(String message) {
        this.message = message;
    }
}
