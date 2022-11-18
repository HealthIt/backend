package com.swygbro.healthit.common.enumType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {

    FOOD_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "조회하지 않는 음식 식별 값입니다."),
    ;

    private final int errorCode;
    private final String message;
}
