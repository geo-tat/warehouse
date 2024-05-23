package ru.mediasoft.warehouse.product.search.criteria;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.mediasoft.warehouse.product.search.strategy.BigDecimalPredicateStrategy;
import ru.mediasoft.warehouse.product.search.strategy.PredicateStrategy;
import ru.mediasoft.warehouse.product.search.enums.OperationType;

import java.math.BigDecimal;

@Data
@Builder
public class BigDecimalSearchCriteria implements SearchCriteria<BigDecimal> {
    private static final PredicateStrategy<BigDecimal> strategy = new BigDecimalPredicateStrategy();

    private final String field;

    private final OperationType operation;

    @NotNull
    private final BigDecimal value;


    @Override
    public PredicateStrategy<BigDecimal> getStrategy() {
        return strategy;
    }
}
