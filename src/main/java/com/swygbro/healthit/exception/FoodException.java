package com.swygbro.healthit.exception;

import com.swygbro.healthit.common.enumType.ErrorResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FoodException extends RuntimeException {

    private final ErrorResult errorResult;
}
