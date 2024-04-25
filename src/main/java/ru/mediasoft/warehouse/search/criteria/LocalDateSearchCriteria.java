package ru.mediasoft.warehouse.search.criteria;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.mediasoft.warehouse.search.enums.OperationType;
import ru.mediasoft.warehouse.search.strategy.LocalDatePredicateStrategy;
import ru.mediasoft.warehouse.search.strategy.PredicateStrategy;

import java.time.LocalDate;

@Data
@Builder
public class LocalDateSearchCriteria implements SearchCriteria<LocalDate> {
    private static final PredicateStrategy<LocalDate> strategy = new LocalDatePredicateStrategy();

    private final String field;

    private final String operation;

    @NotNull
    private final LocalDate value;


    @Override
    public String getField() {
        return field;
    }

    @Override
    public OperationType getOperation() {
        return OperationType.fromString(operation);
    }

    @Override
    public LocalDate getValue() {
        return value;
    }

    @Override
    public PredicateStrategy<LocalDate> getStrategy() {
        return strategy;
    }
}
