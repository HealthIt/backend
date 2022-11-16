package com.swygbro.healthit.controller;

import com.swygbro.healthit.common.GlobalExceptionHandler;
import com.swygbro.healthit.controller.dto.BmiRequestDto;
import com.swygbro.healthit.controller.dto.BmiResultFoodDto;
import com.swygbro.healthit.food.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FoodApiControllerTest {

    @InjectMocks
    private FoodApiController foodApiController;

    @Mock
    private FoodService foodService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodApiController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @ParameterizedTest
    @MethodSource("invalidBmiRequestDtoParameter")
    public void BMI음식조회실패_잘못된파라미터(final BmiRequestDto dto) throws Exception {
        // given
        final String url = "/foods/v1/bmi" +
                "?count=" + dto.getCount() +
                "&gender=" + dto.getGender() +
                "&bmi=" + dto.getBmi();

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidBmiRequestDtoParameter() {
        return Stream.of(
                Arguments.arguments(new BmiRequestDto(0, 0, 24)),
                Arguments.arguments(new BmiRequestDto(-1, 0, 24)),
                Arguments.arguments(new BmiRequestDto(5, 2, 24)),
                Arguments.arguments(new BmiRequestDto(5, 1, -1))
        );
    }

    @Test
    public void BMI음식조회성공() throws Exception {
        // given
        BmiRequestDto dto = new BmiRequestDto(5, 0, 24);

        final String url = "/foods/v1/bmi" +
                "?count=" + dto.getCount() +
                "&gender=" + dto.getGender() +
                "&bmi=" + dto.getBmi();

        doReturn(Arrays.asList(
                new BmiResultFoodDto(),
                new BmiResultFoodDto()
        )).when(foodService).findFoodByBmi(dto);

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
        );

        // then
        resultActions.andExpect(status().isOk());
    }
}