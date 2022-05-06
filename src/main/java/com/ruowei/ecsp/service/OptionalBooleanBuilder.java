package com.ruowei.ecsp.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.function.Function;

public class OptionalBooleanBuilder {

    private final BooleanBuilder predicate;

    public OptionalBooleanBuilder() {
        this.predicate = new BooleanBuilder();
    }

    public OptionalBooleanBuilder(BooleanBuilder predicate) {
        this.predicate = predicate;
    }

    public <T> OptionalBooleanBuilder notNullAnd(Function<T, BooleanExpression> expressionFunction, T value) {
        if (value != null) {
            return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(value)));
        }
        return this;
    }

    public <T> OptionalBooleanBuilder notEmptyAnd(Function<T, BooleanExpression> expressionFunction, T value) {
        if (!ObjectUtils.isEmpty(value)) {
            return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(value)));
        }
        return this;
    }

    public <T> OptionalBooleanBuilder notEmptyAnd(Function<Collection<T>, BooleanExpression> expressionFunction, Collection<T> collection) {
        if (!CollectionUtils.isEmpty(collection)) {
            return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(collection)));
        }
        return this;
    }

    public <T> OptionalBooleanBuilder and(Function<T, BooleanExpression> expressionFunction, T value) {
        return new OptionalBooleanBuilder(predicate.and(expressionFunction.apply(value)));
    }

    public BooleanBuilder build() {
        return predicate;
    }
}
