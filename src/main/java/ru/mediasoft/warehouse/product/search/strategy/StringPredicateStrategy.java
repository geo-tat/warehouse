package ru.mediasoft.warehouse.product.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class StringPredicateStrategy implements PredicateStrategy<String> {
    @Override
    public Predicate getEqPattern(Expression<String> expression, String value, CriteriaBuilder criteria) {
        return criteria.equal(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<String> expression, String value, CriteriaBuilder criteria) {
        return criteria.like(expression, "%" + value + "%");
    }

    @Override
    public Predicate getLefLimitPattern(Expression<String> expression, String value, CriteriaBuilder criteria) {
        return criteria.like(expression, value + "%");
    }

    @Override
    public Predicate getRightLimitPattern(Expression<String> expression, String value, CriteriaBuilder criteria) {
        return criteria.lessThanOrEqualTo(expression, "%" + value);
    }
}
