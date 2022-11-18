package com.swygbro.healthit.controller.dto;

import com.swygbro.healthit.food.domian.Food;
import lombok.Data;

/**
 * 음식 상세정보 Dto
 */
@Data
public class FoodDetailDto {

    private String foodNm;
    private String foodDesc;
    private Integer calorie;
    private Integer protein;
    private Integer carbs;
    private Integer fat;

    public FoodDetailDto(Food food) {
        this.foodNm = food.getFoodNm();
        this.foodDesc = food.getFoodDesc();
        this.calorie = food.getCalorie();
        this.protein = food.getProtein();
        this.carbs = food.getCarbs();
        this.fat = food.getFat();
    }
}
