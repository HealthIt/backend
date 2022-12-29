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
    private String img;
    private Integer calorie;
    private Integer carbs;
    private Integer protein;
    private Integer fat;

    public FoodDetailDto(Food food) {
        this.foodNm = food.getFoodNm();
        this.foodDesc = food.getFoodDesc();
        this.img = food.getImg();
        this.calorie = food.getCalorie();
        this.carbs = food.getCarbs();
        this.protein = food.getProtein();
        this.fat = food.getFat();
    }
}
