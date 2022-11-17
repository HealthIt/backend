package com.swygbro.healthit.food.service;

import com.swygbro.healthit.controller.dto.BmiRequestDto;
import com.swygbro.healthit.controller.dto.BmiResponseDto;
import com.swygbro.healthit.controller.dto.FoodRequestDto;
import com.swygbro.healthit.controller.dto.FoodResponseDto;
import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.food.domian.FoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @InjectMocks
    private FoodService foodService;

    @Mock
    private FoodRepository foodRepository;

    @Test
    public void BM추천음식조회() {
        // given
        ArrayList<Food> data = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            final Food food = Food.builder()
                    .id((long) i)
                    .foodNm("음식")
                    .foodDesc("음식소개")
                    .calorie(200 + (i * 20))
                    .protein(50)
                    .carbs(50)
                    .fat(50)
                    .img("imageUrlData")
                    .build();

            data.add(food);
        }

        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "calorie"));
        doReturn(new PageImpl<>(data))
                .when(foodRepository)
                .findByCalorieBefore(400, pageRequest);

        BmiRequestDto request = new BmiRequestDto(5, 0, 24.0);

        // when
        List<BmiResponseDto> result = foodService.findFoodByBmi(request);

        // then
        assertThat(result.size()).isEqualTo(5);
        for (BmiResponseDto bmiResultFoodDto : result) {
            assertThat(bmiResultFoodDto.getCalorie()).isLessThanOrEqualTo(400);
        }
    }

    @Test
    public void 음식목록조회() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "calorie"));

        Page<FoodResponseDto> data = PageableExecutionUtils.getPage(
                List.of(new FoodResponseDto(-1L, "음식명", "음식소개", "imageUrlData")),
                pageRequest,
                () -> 4
        );

        doReturn(data).when(foodRepository).findFoodByContainIrdntNm("재료명", pageRequest);

        FoodRequestDto request = new FoodRequestDto("재료명", 15.0, 0, 3);

        // when
        Page<FoodResponseDto> result = foodService.findFoodByIrdntNm(request);

        // then
        assertThat(result.getContent().size()).isEqualTo(1);
        assertThat(result.getContent().get(0).getFoodNm()).isEqualTo("음식명");
        assertThat(result.getContent().get(0).getImg()).isEqualTo("imageUrlData");
        assertThat(result.getContent().get(0).getFoodDesc()).isEqualTo("음식소개");
    }
}