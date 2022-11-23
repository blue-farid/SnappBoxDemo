package com.snapp.boxdemo.exception.handler;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * custom unexpected exception handler
 */
@Slf4j
@ControllerAdvice
@Order
public class BoxDemoUnexpectedExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDto<Object>> handleUnexpectedException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(BaseResponseDto.builder().message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
