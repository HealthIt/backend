package com.swygbro.healthit.controller;

import com.swygbro.healthit.common.GlobalExceptionHandler;
import com.swygbro.healthit.controller.dto.*;
import com.swygbro.healthit.exception.FoodException;
import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.food.domian.FoodRepository;
import com.swygbro.healthit.food.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.swygbro.healthit.common.enumType.ErrorResult.FOOD_NOT_FOUND;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(RestDocumentationExtension.class)
class FoodApiControllerTest {

    @InjectMocks
    private FoodApiController foodApiController;

    @Mock
    private FoodService foodService;

    @Mock
    private FoodRepository foodRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(foodApiController)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .setControllerAdvice(new GlobalExceptionHandler())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void BMI??????????????????_COUNT??????() throws Exception {
        // given
        final BmiRequestDto dto = new BmiRequestDto(-1, 0, 24.0);

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
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("count").description("?????? ?????? ??????"),
                                parameterWithName("gender").description("??????"),
                                parameterWithName("bmi").description("BMI")
                        )
                ));
    }

    @Test
    public void BMI??????????????????_??????2??????() throws Exception {
        // given
        final BmiRequestDto dto = new BmiRequestDto(5, 2, 24.0);

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
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("count").description("?????? ?????? ??????"),
                                parameterWithName("gender").description("??????"),
                                parameterWithName("bmi").description("BMI")
                        )
                ));
    }

    @Test
    public void BMI??????????????????_BMI??????() throws Exception {
        // given
        final BmiRequestDto dto = new BmiRequestDto(5, 1, -1.0);

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
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("count").description("?????? ?????? ??????"),
                                parameterWithName("gender").description("??????"),
                                parameterWithName("bmi").description("BMI")
                        )
                ));
    }

    @Test
    public void BMI??????????????????() throws Exception {
        // given
        BmiRequestDto dto = new BmiRequestDto(3, 0, 24.0);

        final String url = "/foods/v1/bmi" +
                "?count=" + dto.getCount() +
                "&gender=" + dto.getGender() +
                "&bmi=" + dto.getBmi();

        List<BmiResponseDto> returnData = new ArrayList<>();

        List<String> irdntNms = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            irdntNms.add("??????" + i);
        }

        for (int i = 1; i < 4; i++) {
            BmiResponseDto data = new BmiResponseDto();
            data.setId((long) i);
            data.setFoodNm("?????????" + i);
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
                                parameterWithName("count").description("?????? ?????? ??????"),
                                parameterWithName("gender").description("??????"),
                                parameterWithName("bmi").description("BMI")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("?????????"),
                                fieldWithPath("data.[].id").type(JsonFieldType.NUMBER)
                                        .description("?????? ID"),
                                fieldWithPath("data.[].foodNm").type(JsonFieldType.STRING)
                                        .description("?????????"),
                                fieldWithPath("data.[].calorie").type(JsonFieldType.NUMBER)
                                        .description("?????????"),
                                fieldWithPath("data.[].img").type(JsonFieldType.STRING)
                                        .description("?????? ?????????"),
                                fieldWithPath("data.[].irdntNames").type(JsonFieldType.ARRAY)
                                        .description("????????? ??????")
                        )));
    }

    @Test
    public void ????????????????????????_BMI??????() throws Exception {
        // given
        final FoodRequestDto dto = new FoodRequestDto("INGREDIENT_NAME", -20.0, 0, 3);

        final String url = "/foods/v1" +
                "?irdntNm=" + dto.getIrdntNm() +
                "&bmi=" + dto.getBmi() +
                "&page=" + dto.getPage() +
                "&size=" + dto.getSize();

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andDo(document("v1/foods/fail/bmi_nagative",
                        preprocessRequest(modifyUris()
                                .host("52.78.0.222")
                                .removePort()
                        ),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("irdntNm").description("????????????"),
                                parameterWithName("bmi").description("BMI"),
                                parameterWithName("page").description("?????? ?????????"),
                                parameterWithName("size").description("?????? ?????????")
                        )));
    }

    @Test
    public void ????????????????????????_??????????????????() throws Exception {
        // given
        final FoodRequestDto dto = new FoodRequestDto();

        final String url = "/foods/v1";

        Page<FoodResponseDto> data = PageableExecutionUtils.getPage(
                Arrays.asList(
                        new FoodResponseDto(1L, "?????????1", "????????????", "data:image/png;base64,DATA"),
                        new FoodResponseDto(2L, "?????????2", "????????????", "data:image/png;base64,DATA"),
                        new FoodResponseDto(3L, "?????????2", "????????????", "data:image/png;base64,DATA")
                ),
                PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "calorie")),
                () -> 4
        );

        doReturn(data).when(foodService).findFoodByIrdntNm(dto);

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url)
        );

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void ????????????????????????() throws Exception {
        // given
        final FoodRequestDto dto = new FoodRequestDto("INGREDIENT_NAME", 15.0, 0, 3);

        final String url = "/foods/v1/" +
                "?irdntNm=" + dto.getIrdntNm() +
                "&bmi=" + dto.getBmi() +
                "&page=" + dto.getPage() +
                "&size=" + dto.getSize();

        Page<FoodResponseDto> data = PageableExecutionUtils.getPage(
                Arrays.asList(
                        new FoodResponseDto(1L, "????????????????????? ???????????????&?????? ?????? ?????? ?????????", "????????????", "data:image/png;base64,DATA"),
                        new FoodResponseDto(2L, "???????????? ???????????????&???????????? ?????????", "????????????", "data:image/png;base64,DATA"),
                        new FoodResponseDto(3L, "????????????&?????? ???????????? ??????", "????????????", "data:image/png;base64,DATA")
                ),
                PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "calorie")),
                () -> 4
        );

        doReturn(data).when(foodService).findFoodByIrdntNm(dto);

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("v1/foods/success",
                        preprocessRequest(modifyUris()
                                .host("52.78.0.222")
                                .removePort()
                        ),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("irdntNm").description("????????????"),
                                parameterWithName("bmi").description("BMI"),
                                parameterWithName("page").description("?????? ?????????"),
                                parameterWithName("size").description("?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.content.[].id").type(JsonFieldType.NUMBER).description("?????? ID"),
                                fieldWithPath("data.content.[].foodNm").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.content.[].foodDesc").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.content.[].img").type(JsonFieldType.STRING).description("?????? ?????????"),

                                fieldWithPath("data.pageable.offset").ignored(),
                                fieldWithPath("data.pageable.pageSize").ignored(),
                                fieldWithPath("data.pageable.pageNumber").ignored(),
                                fieldWithPath("data.pageable.paged").ignored(),
                                fieldWithPath("data.pageable.unpaged").ignored(),
                                fieldWithPath("data.pageable.sort.sorted").ignored(),
                                fieldWithPath("data.pageable.sort.unsorted").ignored(),
                                fieldWithPath("data.pageable.sort.empty").ignored(),
                                fieldWithPath("data.sort.empty").ignored(),
                                fieldWithPath("data.sort.sorted").ignored(),
                                fieldWithPath("data.sort.unsorted").ignored(),
                                fieldWithPath("data.totalPages").ignored(),
                                fieldWithPath("data.size").ignored(),
                                fieldWithPath("data.number").ignored(),
                                fieldWithPath("data.first").ignored(),
                                fieldWithPath("data.last").ignored(),
                                fieldWithPath("data.numberOfElements").ignored(),
                                fieldWithPath("data.empty").ignored(),
                                fieldWithPath("data.totalElements").ignored()
                        )));
    }

    @Test
    public void ??????????????????????????????_???????????????????????????() throws Exception {
        // given
        final String url = "/foods/v1/{id}";

        doThrow(new FoodException(FOOD_NOT_FOUND))
                .when(foodRepository)
                .findById(-1L);

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url, -1)
        );

        // then
        resultActions.andExpect(status().isBadRequest())
                .andDo(document("v1/foods/detail/fail/not_found_foodId",
                        preprocessRequest(modifyUris()
                                .host("52.78.0.222")
                                .removePort()
                        ),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("?????? ?????????")
                        )));
    }

    @Test
    public void ??????????????????????????????() throws Exception {
        // given
        final String url = "/foods/v1/{id}";

        doReturn(Optional.of(Food.builder().foodNm("?????????")
                .foodDesc("????????????")
                .calorie(300)
                .protein(50)
                .carbs(50)
                .fat(50)
                .img("data:image/png;base64,DATA")
                .build())
        ).when(foodRepository)
                .findById(-1L);

        // when
        final ResultActions resultActions = mockMvc.perform(
                get(url, -1)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andDo(document("v1/foods/detail/success",
                        preprocessRequest(modifyUris()
                                .host("52.78.0.222")
                                .removePort()
                        ),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.foodNm").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("data.img").type(JsonFieldType.STRING).description("?????? ?????????"),
                                fieldWithPath("data.foodDesc").type(JsonFieldType.STRING).description("????????????"),
                                fieldWithPath("data.calorie").type(JsonFieldType.NUMBER).description("?????????"),
                                fieldWithPath("data.carbs").type(JsonFieldType.NUMBER).description("????????????"),
                                fieldWithPath("data.protein").type(JsonFieldType.NUMBER).description("?????????"),
                                fieldWithPath("data.fat").type(JsonFieldType.NUMBER).description("??????")
                        )));
    }
}