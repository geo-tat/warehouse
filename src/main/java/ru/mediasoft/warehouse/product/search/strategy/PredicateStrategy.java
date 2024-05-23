package ru.mediasoft.warehouse.product.search.strategy;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public interface PredicateStrategy<T> {

    Predicate getEqPattern(Expression<T> expression, T value, CriteriaBuilder criteria);


    Predicate getLefLimitPattern(Expression<T> expression, T value, CriteriaBuilder criteria);


    Predicate getRightLimitPattern(Expression<T> expression, T value, CriteriaBuilder criteria);


    Predicate getLikePattern(Expression<T> expression, T value, CriteriaBuilder criteria);
}
