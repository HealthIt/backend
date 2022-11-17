package com.swygbro.healthit.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 음식 목록 조회 Dto
 * (size default 10)
 */
@Data
@AllArgsConstructor
public class FoodRequestDto {

    private String irdntNm;

    @Min(value = 0, message = "BMI는 0 이상이어야 합니다.")
    private Double bmi;

    private int page;

    private int size = 10;
}
