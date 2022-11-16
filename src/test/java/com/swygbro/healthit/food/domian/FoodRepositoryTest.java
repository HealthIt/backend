package com.swygbro.healthit.food.domian;

import com.swygbro.healthit.ingredient.domain.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
}