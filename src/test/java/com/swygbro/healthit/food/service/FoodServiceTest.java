package com.swygbro.healthit.food.service;

import com.swygbro.healthit.controller.dto.BmiRequestDto;
import com.swygbro.healthit.controller.dto.BmiResultFoodDto;
import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.food.domian.FoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    public void BMI음식조회() {
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

        BmiRequestDto request = new BmiRequestDto(5, 0, 24);

        // when
        List<BmiResultFoodDto> result = foodService.findFoodByBmi(request);

        // then
        assertThat(result.size()).isEqualTo(5);
        for (BmiResultFoodDto bmiResultFoodDto : result) {
            assertThat(bmiResultFoodDto.getCalorie()).isLessThanOrEqualTo(400);
        }
    }
}