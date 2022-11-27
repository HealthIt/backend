package com.swygbro.healthit.controller.view;

import com.swygbro.healthit.controller.dto.FoodSaveDto;
import com.swygbro.healthit.exception.FoodException;
import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.food.domian.FoodRepository;
import com.swygbro.healthit.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.swygbro.healthit.common.enumType.ErrorResult.FOOD_NOT_FOUND;

/**
 * 음식 관리  Controller
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class FoodController {

    /**
     * 음식 관리 Service
     */
    private final FoodService foodService;

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

    /**
     * 음식 수정 View 호출
     * @param foodId 조회 음식 식별 값
     * @param model Model 객체
     * @return Url
     */
    @GetMapping("/food/{id}")
    public String edit(@PathVariable("id") Long foodId, Model model) {
        final Food food = foodRepository.findFetchById(foodId).orElseThrow(() -> {
            throw new FoodException(FOOD_NOT_FOUND);
        });
        model.addAttribute("food", food);

        return "food/foodEdit";
    }

    /**
     * 음식 수정
     * @param foodId 조회 음식 식별 값
     * @param dto 음식 수정정보
     * @return Url
     */
    @PostMapping("/food/{id}")
    public String updateFood(@PathVariable("id") Long foodId,
                             @ModelAttribute("food") FoodSaveDto dto) {

        foodService.updateFood(foodId, dto);

        return "redirect:/admin/food";
    }
}
