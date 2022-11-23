package com.swygbro.healthit.controller.view;

import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.food.domian.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 음식 관리  Controller
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class FoodController {

    /**
     * 음식 관린 Repository
     */
    private final FoodRepository foodRepository;

    /**
     * 음식 저장 View 호출
     * @return Url
     */
    @GetMapping("/food/new")
    public String createFrom() {
        return "food/createFoodForm";
    }

    /**
     * 음식 목록 View 호출
     * @param model Model 객체
     * @return Url
     */
    @GetMapping("/food")
    public String list(Model model) {
        final List<Food> foods = foodRepository.findAll();
        model.addAttribute("foods", foods);

        return "food/foodList";
    }
}
