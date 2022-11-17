package com.swygbro.healthit.food.domian;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.swygbro.healthit.common.util.QuerydslUtils;
import com.swygbro.healthit.controller.dto.FoodResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.swygbro.healthit.food.domian.QFood.food;
import static com.swygbro.healthit.ingredient.domain.QIngredient.ingredient;

@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FoodResponseDto> findFoodByContainIrdntNm(String irdntNm, Pageable pageable) {

        final List<OrderSpecifier<?>> orders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            OrderSpecifier<?> orderId = QuerydslUtils.getSortedColumn(direction, food.calorie, "calorie");
            orders.add(orderId);
        }

        OrderSpecifier[] orderSpecifiers = orders.toArray(OrderSpecifier[]::new);

        List<FoodResponseDto> content = queryFactory
                .select(Projections.constructor(FoodResponseDto.class,
                        food.id,
                        food.foodNm,
                        food.img,
                        food.foodDesc
                ))
                .from(ingredient)
                .join(ingredient.food, food)
                .where(irdntNmContains(irdntNm))
                .groupBy(ingredient.food.id)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(ingredient.food.id.countDistinct())
                .from(ingredient)
                .where(irdntNmContains(irdntNm));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression irdntNmContains(String irdntNm) {
        return StringUtils.hasText(irdntNm) ? ingredient.irdntNm.contains(irdntNm) : null;
    }
}