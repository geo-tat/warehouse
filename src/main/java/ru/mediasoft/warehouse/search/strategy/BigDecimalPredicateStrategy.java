package ru.mediasoft.warehouse.search.strategy;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;


public class BigDecimalPredicateStrategy implements PredicateStrategy<BigDecimal> {

    @Override
    public Predicate getEqPattern(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteria) {
        return criteria.equal(expression, value);
    }

    @Override
    public Predicate getLefLimitPattern(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteria) {
        return criteria.greaterThanOrEqualTo(expression, value);
    }


    @Override
    public Predicate getRightLimitPattern(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteria) {
        return criteria.lessThanOrEqualTo(expression, value);
    }

    @Override
    public Predicate getLikePattern(Expression<BigDecimal> expression, BigDecimal value, CriteriaBuilder criteria) {
        return criteria.and(
                criteria.greaterThanOrEqualTo(expression, value.multiply(BigDecimal.valueOf(0.9))),
                criteria.lessThanOrEqualTo(expression, value.multiply(BigDecimal.valueOf(1.1)))
        );
    }
}
