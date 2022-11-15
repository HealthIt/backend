package com.swygbro.healthit.controller.dto;

import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.ingredient.domain.Ingredient;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 음식정보 저장 Dto
 * (식재료명 포함)
 */
@Data
public class FoodSaveDto {

    @NotNull
    private String foodNm;            // 음식명
    private String foodDesc;            // 음식 소개
    private String img;             // 음식 사진

    @Min(0)
    @NotNull
    private Integer calorie;        // 칼로리
    @Min(0)
    private Integer protein;        // 단백질
    @Min(0)
    private Integer carbs;          // 탄수화물
    @Min(0)
    private Integer fat;            // 지방

    @NotNull
    private List<String> irdnts;    // 식재료 목록

    public Food toEntity() {
        Food entity = Food.builder()
                .foodNm(this.foodNm)
                .foodDesc(this.foodDesc)
                .calorie(this.calorie)
                .protein(this.protein)
                .carbs(this.carbs)
                .fat(this.fat)
                .img(this.img)
                .build();

        for (String name : irdnts) {
            entity.addIngredient(new Ingredient(name));
        }

        return entity;
    }
}
