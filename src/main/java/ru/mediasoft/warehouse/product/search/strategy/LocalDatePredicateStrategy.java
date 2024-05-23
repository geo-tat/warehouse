package ru.mediasoft.warehouse.product.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDate;


public class LocalDatePredicateStrategy implements PredicateStrategy<LocalDate> {
    @Override
    public Predicate getEqPattern(Expression<LocalDate> expression, LocalDate value, CriteriaBuilder criteria) {
        return criteria.equal(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<LocalDate> expression, LocalDate value, CriteriaBuilder criteria) {
        LocalDate startDate = value.minusDays(3);
        LocalDate endDate = value.plusDays(3);
        return criteria.between(expression, criteria.literal(startDate), criteria.literal(endDate));
    }

    @Override
    public Predicate getLefLimitPattern(Expression<LocalDate> expression, LocalDate value, CriteriaBuilder criteria) {
        return criteria.greaterThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getRightLimitPattern(Expression<LocalDate> expression, LocalDate value, CriteriaBuilder criteria) {
        return criteria.lessThanOrEqualTo(expression, value);
    }
}
