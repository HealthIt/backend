package com.swygbro.healthit.food.domian;

import com.swygbro.healthit.controller.dto.FoodResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodRepositoryQuerydsl {

    Page<FoodResponseDto> findFoodByContainIrdntNm(String irdntNm, Pageable pageable);
}
