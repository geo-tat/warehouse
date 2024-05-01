package ru.mediasoft.warehouse.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(final EntityNotFoundException e) {
        log.error("Пользователь с данным ID не найден.", e);
        return new ErrorResponse(e.getClass().getSimpleName(),
                Arrays.stream(e.getStackTrace()).findFirst().toString(),
                e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUniqueSkyException(final DataIntegrityViolationException e) {
        log.error("Товар с данным артикулом уже есть в базе данных", e);
        return new ErrorResponse(e.getClass().getSimpleName(),
                Arrays.stream(e.getStackTrace()).findFirst().toString(),
                "Товар с данным артикулом уже есть в базе данных",
                LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        log.error("Ошибка валидации сущности", e);
        return new ErrorResponse(e.getClass().getSimpleName(),
                Arrays.stream(e.getStackTrace()).findFirst().toString(),
                e.getMessage(),
                LocalDateTime.now());
    }
}