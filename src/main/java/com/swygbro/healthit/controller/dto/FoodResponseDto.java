package com.swygbro.healthit.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 음식 목록 응답 Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponseDto {

    private Long id;
    private String foodNm;
    private String foodDesc;
    private String img;
}
