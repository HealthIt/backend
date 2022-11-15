package com.swygbro.healthit.food.service;

import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.food.domian.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 음식관리 Service
 */
@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public void save(Food food) {
        foodRepository.save(food);
    }
}
