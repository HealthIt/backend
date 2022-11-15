package com.swygbro.healthit.controller;

import com.swygbro.healthit.controller.dto.FoodSaveDto;
import com.swygbro.healthit.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/foods")
public class FoodApiController {

    private final FoodService foodService;

    @PostMapping("/new")
    public ResponseEntity<Void> saveFood(@RequestBody @Validated final FoodSaveDto dto) {
        foodService.save(dto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
