package com.swygbro.healthit.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Bmi를 통한 음식 조회 정보 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BmiRequestDto {

    @Min(value = 0, message = "조회 개수는 0 이상이어야 합니다.")
    private int count;          // 조회 개수

    @Min(value = 0, message = "성별은 남자(0)/여자(1)만 선택 가능합니다.")
    @Max(value = 0, message = "성별은 남자(0)/여자(1)만 선택 가능합니다.")
    @NotNull(message = "성별은 필수 값입니다.")
    private Integer gender;     // 성별

    @Min(value = 0, message = "BMI는 0 이상이어야 합니다.")
    @NotNull(message = "BMI는 필수 값입니다.")
    private Double bmi;         // Bmi
}
