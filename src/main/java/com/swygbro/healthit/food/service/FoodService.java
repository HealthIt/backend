package com.swygbro.healthit.food.service;

import com.swygbro.healthit.common.BmiUtils;
import com.swygbro.healthit.controller.dto.BmiRequestDto;
import com.swygbro.healthit.controller.dto.BmiResultFoodDto;
import com.swygbro.healthit.food.domian.Food;
import com.swygbro.healthit.food.domian.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 음식 관리 Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class FoodService {

    /**
     * 음식 관리 Repository
     */
    private final FoodRepository foodRepository;

    /**
     * 음식 저장
     * @param food 음식 정보
     */
    public void save(Food food) {
        foodRepository.save(food);
    }

    /**
     * Bmi를 통해 알맞은 칼로리 음식 조회
     *
     * @param dto 음식 조회 정보(조회 음식 수, bmi, 성별)
     *            (조회 음식 수 보다 조회된 결과의 수가 적은경우 조회된 결과만 반환)
     * @return 음식 목록
     */
    public List<BmiResultFoodDto> findFoodByBmi(BmiRequestDto dto) {
        PageRequest pageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "calorie"));

        // 최대 칼로리 한끼 평균 값
        int maxCalorie = BmiUtils.convertBmiToKcal(dto.getGender(), dto.getBmi());
        // 칼로리에 맞는 음식 조회(20개)
        Page<Food> foods = foodRepository.findByCalorieBefore(maxCalorie, pageRequest);

        // 조회된 음식 수보다 count가 크거나 같은 경우 조회된 음식만 반환
        if (foods.getTotalElements() <= dto.getCount()) {
            return foods.map(BmiResultFoodDto::new).getContent();
        }

        List<BmiResultFoodDto> result = new ArrayList<>();

        List<Food> content = new ArrayList<>(foods.getContent());

        // 조회된 음식중 랜덤으로 count 만큼 추출
        for(int i=0; i<dto.getCount(); i++) {
            int idx = (int) (Math.random() * (content.size() - 1));

            // 반환 데이터 세팅
            Food food = content.get(idx);
            result.add(new BmiResultFoodDto(food));

            // 추출 데이터 삭제
            content.remove(food);
        }

        return result;
    }
}
