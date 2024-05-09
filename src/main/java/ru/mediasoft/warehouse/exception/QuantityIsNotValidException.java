package ru.mediasoft.warehouse.exception;

import lombok.Getter;

@Getter
public class QuantityIsNotValidException extends RuntimeException {
    private final String s;

    public QuantityIsNotValidException(String s) {
        this.s = s;
    }
}
