package com.swygbro.healthit.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Bmi를 통한 음식 조회 정보 Dto
 */
@Data
@AllArgsConstructor
public class BmiRequestDto {

    @Min(0)
    private int count;      // 조회 개수

    @Min(0)
    @Max(1)
    private int gender;     // 성별

    @NotNull
    @Min(0)
    private double bmi;     // Bmi
}
