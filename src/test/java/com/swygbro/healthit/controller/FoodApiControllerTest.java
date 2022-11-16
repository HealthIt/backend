package com.swygbro.healthit.controller;

import com.swygbro.healthit.common.GlobalExceptionHandler;
import com.swygbro.healthit.controller.dto.BmiRequestDto;
import com.swygbro.healthit.controller.dto.BmiResultFoodDto;
import com.swygbro.healthit.food.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RestDocumentationExtension.class)
class FoodApiControllerTest {

    @InjectMocks
    private FoodApiController foodApiController;

    @Mock
    private FoodService foodService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(foodApiController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void BMI음식조회실패_COUNT음수() throws Exception {
        // given
        final BmiRequestDto dto = new BmiRequestDto(-1, 0, 24);

        final String url = "/foods/v1/bmi" +
                "?count=" + dto.getCount() +
                "&gender=" + dto.getGender() +
                "&bmi=" + dto.getBmi();

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url)
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andDo(document("v1/foods/bmi/fail/count_negative",
                        preprocessRequest(modifyUris()
                                .host("52.78.0.222")
                                .removePort()
                        ),
                        requestParameters(
                                parameterWithName("count").description("추천 음식 개수"),
                                parameterWithName("gender").description("성별"),
                                parameterWithName("bmi").description("BMI")
                        )
                ));
    }

    @Test
    public void BMI음식조회실패_성별2이상() throws Exception {
        // given
        final BmiRequestDto dto = new BmiRequestDto(5, 2, 24);

        final String url = "/foods/v1/bmi" +
                "?count=" + dto.getCount() +
                "&gender=" + dto.getGender() +
                "&bmi=" + dto.getBmi();

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url)
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andDo(document("v1/foods/bmi/fail/gender_more_then_2",
                        preprocessRequest(modifyUris()
                                .host("52.78.0.222")
                                .removePort()
                        ),
                        requestParameters(
                                parameterWithName("count").description("추천 음식 개수"),
                                parameterWithName("gender").description("성별"),
                                parameterWithName("bmi").description("BMI")
                        )
                ));
    }

    @Test
    public void BMI음식조회실패_BMI음수() throws Exception {
        // given
        final BmiRequestDto dto = new BmiRequestDto(5, 1, -1);

        final String url = "/foods/v1/bmi" +
                "?count=" + dto.getCount() +
                "&gender=" + dto.getGender() +
                "&bmi=" + dto.getBmi();

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url)
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andDo(document("v1/foods/bmi/fail/bmi_nagative",
                        preprocessRequest(modifyUris()
                                .host("52.78.0.222")
                                .removePort()
                        ),
                        requestParameters(
                                parameterWithName("count").description("추천 음식 개수"),
                                parameterWithName("gender").description("성별"),
                                parameterWithName("bmi").description("BMI")
                        )
                ));
    }

    @Test
    public void BMI음식조회성공() throws Exception {
        // given
        BmiRequestDto dto = new BmiRequestDto(3, 0, 24);

        final String url = "/foods/v1/bmi" +
                "?count=" + dto.getCount() +
                "&gender=" + dto.getGender() +
                "&bmi=" + dto.getBmi();

        List<BmiResultFoodDto> returnData = new ArrayList<>();

        List<String> irdntNms = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            irdntNms.add("재료" + i);
        }

        for (int i = 1; i < 4; i++) {
            BmiResultFoodDto data = new BmiResultFoodDto();
            data.setId((long) i);
            data.setFoodName("음식명" + i);
            data.setCalorie(100);
            data.setImg("data:image/png;base64,DATA");
            data.setIrdntNames(irdntNms);

            returnData.add(data);
        }

        doReturn(returnData).when(foodService).findFoodByBmi(dto);

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("v1/foods/bmi/success",
                        preprocessRequest(modifyUris()
                                .host("52.78.0.222")
                                .removePort()
                        ),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("count").description("추천 음식 개수"),
                                parameterWithName("gender").description("성별"),
                                parameterWithName("bmi").description("BMI")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER)
                                        .description("음식 ID"),
                                fieldWithPath("data.[].foodName").type(JsonFieldType.STRING)
                                        .description("음식명"),
                                fieldWithPath("data.[].calorie").type(JsonFieldType.NUMBER)
                                        .description("칼로리"),
                                fieldWithPath("data.[].img").type(JsonFieldType.STRING)
                                        .description("음식 이미지"),
                                fieldWithPath("data.[].irdntNames").type(JsonFieldType.ARRAY)
                                        .description("식재료 목록")
                        )));
    }
}