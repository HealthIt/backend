package com.swygbro.healthit.food.domian;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;
}
