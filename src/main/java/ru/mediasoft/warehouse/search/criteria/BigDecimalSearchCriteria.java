package ru.mediasoft.warehouse.search.criteria;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.mediasoft.warehouse.search.enums.OperationType;
import ru.mediasoft.warehouse.search.strategy.BigDecimalPredicateStrategy;
import ru.mediasoft.warehouse.search.strategy.PredicateStrategy;

import java.math.BigDecimal;

@Data
@Builder
public class BigDecimalSearchCriteria implements SearchCriteria<BigDecimal> {
    private static final PredicateStrategy<BigDecimal> strategy = new BigDecimalPredicateStrategy();

    private final String field;

    private final String operation;

    @NotNull
    private final BigDecimal value;


    @Override
    public String getField() {
        return field;
    }

    @Override
    public OperationType getOperation() {
        return OperationType.fromString(operation);
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public PredicateStrategy<BigDecimal> getStrategy() {
        return strategy;
    }
}
