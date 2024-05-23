package ru.mediasoft.warehouse.exception;

import lombok.Getter;

@Getter
public class CustomerAccessException extends RuntimeException {
    private final String s;

    public CustomerAccessException(String s) {
        this.s = s;
    }
}
