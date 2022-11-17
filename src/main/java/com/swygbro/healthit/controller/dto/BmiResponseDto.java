package com.swygbro.healthit.controller.dto;

import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.ingredient.domain.Ingredient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Bmi를 통한 조회 결과 반환 Dto
 */
@Data
@NoArgsConstructor
public class BmiResponseDto {

    private Long id;
    private String foodNm;            // 음식명
    private Integer calorie;            // 칼로리
    private String img;                 // 음식 이미지
    private List<String> irdntNames;    // 재료명

    public BmiResponseDto(Food food) {
        this.id = food.getId();
        this.foodNm = food.getFoodNm();
        this.calorie = food.getCalorie();
        this.img = food.getImg();
        this.irdntNames = food.getIngredients().stream()
                .map(Ingredient::getIrdntNm)
                .collect(toList());
    }
}
