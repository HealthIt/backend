package com.swygbro.healthit.common.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;

public class QuerydslUtils {

    public static OrderSpecifier<?> getSortedColumn(final Order order,
                                                    final Path<?> parent,
                                                    final String fieldName) {

        final Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }
}
