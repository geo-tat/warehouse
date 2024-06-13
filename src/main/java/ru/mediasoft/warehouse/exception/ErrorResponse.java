package ru.mediasoft.warehouse.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorResponse {
    private String exception;
    private String sourceClass;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String exception, String sourceClass, String message, LocalDateTime timestamp) {
        this.exception = exception;
        this.sourceClass = sourceClass;
        this.message = message;
        this.timestamp = timestamp;
    }
}
