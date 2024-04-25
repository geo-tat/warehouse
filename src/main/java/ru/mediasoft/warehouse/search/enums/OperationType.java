package ru.mediasoft.warehouse.search.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OperationType {
    EQUAL("="),
    GREATER_THAN_OR_EQ(">="),
    LESS_THAN_OR_EQ("<="),
    LIKE("~");

    private final String operation;

    OperationType(String operation) {
        this.operation = operation;
    }

    @JsonCreator
    public static OperationType fromString(String text) {
        return switch (text.toUpperCase()) {
            case "=", "EQUAL" -> EQUAL;
            case ">=", "GREATER_THAN_OR_EQ" -> GREATER_THAN_OR_EQ;
            case "<=", "LESS_THAN_OR_EQ" -> LESS_THAN_OR_EQ;
            case "~", "LIKE" -> LIKE;
            default -> throw new IllegalArgumentException("No constant with text " + text + " found");
        };
    }
}
