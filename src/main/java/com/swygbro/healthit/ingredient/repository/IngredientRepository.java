package com.swygbro.healthit.ingredient.repository;

import com.swygbro.healthit.ingredient.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 식재료 관린 Repository
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
