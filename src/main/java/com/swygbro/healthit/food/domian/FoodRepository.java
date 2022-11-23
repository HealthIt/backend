package com.swygbro.healthit.food.domian;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 음식 관린 Repository
 */
public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryQuerydsl {

    Page<Food> findByCalorieBefore(final int calorie, final Pageable pageable);

    @Query("select f from Food f left join fetch f.ingredients i where f.id = :id")
    Optional<Food> findFetchById(@Param("id") final Long foodId);
}
