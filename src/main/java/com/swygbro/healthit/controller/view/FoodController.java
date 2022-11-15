package com.swygbro.healthit.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 음식 관리  Controller
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class FoodController {

    @GetMapping("/food/new")
    public String createFrom() {
        return "food/createFoodForm";
    }
}
