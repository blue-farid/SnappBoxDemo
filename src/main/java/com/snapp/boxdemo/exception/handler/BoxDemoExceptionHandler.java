package com.snapp.boxdemo.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.snapp.boxdemo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.validation.ValidationException;

/**
 * Custom Exception handler
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
public class BoxDemoExceptionHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);
        return "";
    }

    @ExceptionHandler({ServletException.class, TypeMismatchException.class, IllegalArgumentException.class, JsonParseException.class})
    public String handleBadApiCallException(Exception e) {
        log.error(e.getMessage(), e);
        return "";
    }

    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(Exception e) {
        log.error(e.getMessage(), e);
        return "";
    }
}
