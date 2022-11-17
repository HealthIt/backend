package com.swygbro.healthit.food.domian;

import com.swygbro.healthit.controller.dto.FoodResponseDto;
import com.swygbro.healthit.ingredient.domain.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Commit
class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;

    @Test
    public void 음식조회_칼로리400이하() {
        // given
        for (int i = 0; i < 10; i++) {
            final Food food = Food.builder()
                    .foodNm("음식")
                    .foodDesc("소개")
                    .calorie(260 + (i * 20))
                    .protein(50)
                    .carbs(50)
                    .fat(50)
                    .img("imageUrlData")
                    .build();

            food.addIngredient(new Ingredient("재료1"));


            foodRepository.save(food);
        }

        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "calorie"));

        // when
        final Page<Food> result = foodRepository.findByCalorieBefore(400, pageRequest);

        // then
        assertThat(result.getSize()).isEqualTo(20);
        assertThat(result.getTotalElements()).isEqualTo(7L);
        assertThat(result.getContent()).extracting("calorie").containsExactly(380, 360, 340, 320, 300, 280, 260);
    }

    @Test
    public void 음식목록조회() {
        // given
        for (int i = 1; i < 10; i++) {
            Food food = Food.builder()
                    .foodNm("음식명" + i)
                    .calorie(10 * i)
                    .build();

            for (int j = 0; j < i; j++) {
                food.addIngredient(new Ingredient("재료" + j));
            }

            foodRepository.save(food);
        }

        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "calorie"));

        // when
        Page<FoodResponseDto> result = foodRepository.findFoodByContainIrdntNm("재료5", pageRequest);

        // then
        assertThat(result.getTotalElements()).isEqualTo(4);
        assertThat(result.getContent()).extracting("foodNm").containsExactly("음식명9", "음식명8", "음식명7");
    }
}