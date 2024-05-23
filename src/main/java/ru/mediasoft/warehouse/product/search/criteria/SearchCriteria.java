package ru.mediasoft.warehouse.product.search.criteria;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import ru.mediasoft.warehouse.product.search.enums.OperationType;
import ru.mediasoft.warehouse.product.search.strategy.PredicateStrategy;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        include = JsonTypeInfo.As.PROPERTY,
        property = "field"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "name"),
        @JsonSubTypes.Type(value = BigDecimalSearchCriteria.class, name = "price"),
        @JsonSubTypes.Type(value = LocalDateSearchCriteria.class, name = "created"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "sku"),
        @JsonSubTypes.Type(value = StringSearchCriteria.class, name = "description")
})
public interface SearchCriteria<T> {

    String getField();

    @NotNull
    OperationType getOperation();

    @NotNull
    T getValue();

    PredicateStrategy<T> getStrategy();
}
