package ru.mediasoft.warehouse.search.criteria;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ru.mediasoft.warehouse.search.enums.OperationType;
import ru.mediasoft.warehouse.search.strategy.PredicateStrategy;
import ru.mediasoft.warehouse.search.strategy.StringPredicateStrategy;

@Data
@Builder
public class StringSearchCriteria implements SearchCriteria<String> {

    private static final PredicateStrategy<String> strategy = new StringPredicateStrategy();

    private final String field;

    private final String operation;

    @NotBlank
    private final String value;

    @Override
    public String getField() {
        return field;
    }

    @Override
    public OperationType getOperation() {
        return OperationType.fromString(operation);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public PredicateStrategy<String> getStrategy() {
        return strategy;
    }
}
