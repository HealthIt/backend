package com.swygbro.healthit.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> BindExceptionHandle(final BindException ex) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError objectError : ex.getAllErrors()) {
            sb.append(objectError.getDefaultMessage());
            sb.append(System.getProperty("line.separator"));
        }

        log.warn("Invalid DTO Parameter errors: {}", sb);
        return this.makeErrorResponseEntity(sb.toString());
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final String errorResult) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorResult));
    }

    @Getter
    @RequiredArgsConstructor
    static class ErrorResponse {
        private final int code;
        private final String message;
    }
}
