package ru.mediasoft.warehouse.product.search.criteria;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.mediasoft.warehouse.product.search.enums.OperationType;
import ru.mediasoft.warehouse.product.search.strategy.LocalDatePredicateStrategy;
import ru.mediasoft.warehouse.product.search.strategy.PredicateStrategy;

import java.time.LocalDate;

@Data
@Builder
public class LocalDateSearchCriteria implements SearchCriteria<LocalDate> {
    private static final PredicateStrategy<LocalDate> strategy = new LocalDatePredicateStrategy();

    private final String field;

    private final OperationType operation;

    @NotNull
    private final LocalDate value;


    @Override
    public PredicateStrategy<LocalDate> getStrategy() {
        return strategy;
    }
}
