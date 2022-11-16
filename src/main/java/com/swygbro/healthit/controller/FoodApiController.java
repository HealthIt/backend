package com.swygbro.healthit.controller;

import com.swygbro.healthit.controller.dto.BmiRequestDto;
import com.swygbro.healthit.controller.dto.BmiResultFoodDto;
import com.swygbro.healthit.controller.dto.FoodSaveDto;
import com.swygbro.healthit.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 음식 관리 Restcontroller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodApiController {

    private final FoodService foodService;

    /**
     * 음식 데이터 저장
     *
     * @param dto 저장 정보
     * @return CREATE(201)
     */
    @PostMapping("/new")
    public ResponseEntity<Void> saveFood(@RequestBody @Validated final FoodSaveDto dto) {
        foodService.save(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Bmi를 통해 알맞은 칼로리 음식 조회
     *
     * @param dto 음식 조회 정보(조회 음식 수, bmi, 성별)
     *            (조회 음식 수 보다 조회된 결과의 수가 적은경우 조회된 결과만 반환)
     * @return 음식 목록
     */
    @GetMapping("/v1/bmi")
    public ResponseEntity<List<BmiResultFoodDto>> findFoodByBmi(@ModelAttribute @Validated BmiRequestDto dto) {
        List<BmiResultFoodDto> result = foodService.findFoodByBmi(dto);

        return ResponseEntity.ok(result);
    }
}
