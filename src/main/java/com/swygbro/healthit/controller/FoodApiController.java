package com.swygbro.healthit.controller;

import com.swygbro.healthit.common.enumType.ErrorResult;
import com.swygbro.healthit.controller.dto.*;
import com.swygbro.healthit.exception.FoodException;
import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.food.domian.FoodRepository;
import com.swygbro.healthit.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 음식 관리 Restcontroller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodApiController {

    /**
     * 음식 관리 Service
     */
    private final FoodService foodService;

    /**
     * 음식 관리 Repository
     */
    private final FoodRepository foodRepository;

    /**
     * 음식 데이터 저장
     *
     * @param dto 저장 정보
     * @return CREATE(201)
     */
    @PostMapping("/new")
    public ResponseEntity<Void> save(@RequestBody @Valid final FoodSaveDto dto) {
        foodService.save(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 음식 정보 삭제
     *
     * @param foodId 음식 식별값
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") final Long foodId) {
        foodRepository.deleteById(foodId);
    }

    /**
     * Bmi를 통해 알맞은 칼로리 음식 조회
     *
     * @param dto 음식 조회 정보(조회 음식 수, bmi, 성별)
     *            (조회 음식 수 보다 조회된 결과의 수가 적은경우 조회된 결과만 반환)
     * @return 추천음식 목록
     */
    @GetMapping("/v1/bmi")
    public ResponseDto<List<BmiResponseDto>> findFoodByBmi(@ModelAttribute @Valid final BmiRequestDto dto) {
        final List<BmiResponseDto> result = foodService.findFoodByBmi(dto);

        return ResponseDto.of("BMI [" + dto.getBmi() + "] : " +  result.size() + " 개의 추천 음식 조회", result);
    }

    /**
     * 음식 목록 조회
     *
     * @param dto 음식 목록 조회 정보(식재료명, bmi, page, size)
     * @return 음식 목록
     */
    @GetMapping("/v1")
    public ResponseDto<Page<FoodResponseDto>> findFoodList(@ModelAttribute @Valid final FoodRequestDto dto) {
        final Page<FoodResponseDto> result = foodService.findFoodByIrdntNm(dto);

        return ResponseDto.of("음식목록 조회 성공", result);
    }

    /**
     * 음식 상세정보 조회
     *
     * @param foodId 음식 식별값
     * @return 음식 상세정보
     */
    @GetMapping("/v1/{id}")
    public ResponseDto<FoodDetailDto> findFoodDetail(@PathVariable("id") final long foodId) {
        final Food findFood = foodRepository.findById(foodId).orElseThrow(() -> {
            throw new FoodException(ErrorResult.FOOD_NOT_FOUND);
        });

        return ResponseDto.of("음식 상세정보 조회 성공", new FoodDetailDto(findFood));
    }
}
