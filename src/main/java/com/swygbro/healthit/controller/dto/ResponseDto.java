package com.swygbro.healthit.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ResponseDto<T> {

    private final String message;
    private final T data;
}
