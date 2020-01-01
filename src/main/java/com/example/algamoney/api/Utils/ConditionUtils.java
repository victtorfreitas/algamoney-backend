package com.example.algamoney.api.Utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.StringPath;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class ConditionUtils {

    public static Predicate filter(Boolean valueBoolean, BooleanPath expressionBoolean) {
        return Objects.nonNull(valueBoolean) ?
                new BooleanBuilder().and(expressionBoolean.eq(valueBoolean)) :
                new BooleanBuilder();
    }

    public static Predicate filter(String valueString, StringPath expressionString) {
        return StringUtils.isNotEmpty(valueString) ?
                new BooleanBuilder().and(expressionString.likeIgnoreCase("%" + valueString + "%")) :
                new BooleanBuilder();
    }
}
