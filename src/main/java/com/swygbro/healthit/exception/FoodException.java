package com.swygbro.healthit.exception;

import com.swygbro.healthit.common.enumType.ErrorResult;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FoodException extends RuntimeException {

    private final ErrorResult errorResult;
}
