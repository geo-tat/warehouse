package ru.mediasoft.warehouse.product.util;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.mediasoft.warehouse.product.model.Product;
import ru.mediasoft.warehouse.product.search.criteria.SearchCriteria;
import ru.mediasoft.warehouse.product.search.strategy.PredicateStrategy;

import java.util.List;

@RequiredArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private final List<SearchCriteria<?>> criteriaList;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate finalPredicate = cb.conjunction();

        for (SearchCriteria<?> criteria : criteriaList) {
            Predicate predicate = createPredicate(criteria, root, cb);
            finalPredicate = cb.and(finalPredicate, predicate);
        }

        return finalPredicate;
    }

    private <T> Predicate createPredicate(SearchCriteria<T> criteria, Root<Product> root, CriteriaBuilder cb) {
        Expression<T> expression = root.get(criteria.getField());
        PredicateStrategy<T> strategy = criteria.getStrategy();
        T value = criteria.getValue();
        String operation = criteria.getOperation().name();

        return switch (operation) {
            case "EQUAL" -> strategy.getEqPattern(expression, value, cb);
            case "LIKE" -> strategy.getLikePattern(expression, value, cb);
            case "GREATER_THAN_OR_EQ" -> strategy.getLefLimitPattern(expression, value, cb);
            case "LESS_THAN_OR_EQ" -> strategy.getRightLimitPattern(expression, value, cb);
            default -> throw new IllegalArgumentException("Ошибка операции: " + operation);
        };
    }
}
