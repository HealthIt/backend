package com.swygbro.healthit.controller.dto;

import lombok.Data;

/**
 * 음식에 포함된 식재료 정보
 */
@Data
public class IrdntSaveDto {
    private Long id;
    private String irdntNm;
}