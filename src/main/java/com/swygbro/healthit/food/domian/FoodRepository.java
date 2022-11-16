package com.swygbro.healthit.food.domian;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 음식 관린 Repository
 */
public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryQuerydsl {

    Page<Food> findByCalorieBefore(int calorie, Pageable pageable);
}
