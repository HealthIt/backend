package com.swygbro.healthit.common;

import com.swygbro.healthit.common.enumType.ErrorResult;
import com.swygbro.healthit.exception.FoodException;
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
    public ResponseEntity<ErrorResponse> BindExceptionHandle(final BindException ex) {
        final StringBuilder sb = new StringBuilder();
        for (ObjectError objectError : ex.getAllErrors()) {
            sb.append(objectError.getDefaultMessage());
            sb.append(System.getProperty("line.separator"));
        }

        log.warn("Invalid DTO Parameter errors: {}", sb);
        return this.makeErrorResponseEntity(sb.toString());
    }

    @ExceptionHandler({FoodException.class})
    public ResponseEntity<ErrorResponse> FoodExceptionHandle(final FoodException ex) {
        log.warn("FoodException error: {}", ex.getErrorResult().getMessage());

        return this.makeErrorResponseEntity(ex.getErrorResult());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> RuntimeExceptionHandle(final RuntimeException ex) {
        log.warn("FoodException error: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final String errorResult) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorResult));
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(ErrorResult error) {
        return ResponseEntity.status(error.getStatus())
                .body(new ErrorResponse(error.getStatus().value(), error.getMessage()));
    }

    @Getter
    @RequiredArgsConstructor
    static class ErrorResponse {
        private final int code;
        private final String message;
    }
}
