package com.snapp.boxdemo.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
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
    public String handleUnexpectedException(Exception e) {
        log.error(e.getMessage(), e);
        return "";
    }
}
