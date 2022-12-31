package com.snapp.boxdemo.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.snapp.boxdemo.exception.NotFoundException;
import com.snapp.boxdemo.model.dto.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import java.util.Objects;

/**
 * Custom Exception handler
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
public class BoxDemoExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponseDto<Object>> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(BaseResponseDto.builder().message(e.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ServletException.class, TypeMismatchException.class, IllegalArgumentException.class, JsonParseException.class})
    public ResponseEntity<BaseResponseDto<Object>> handleBadApiCallException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(BaseResponseDto.builder().message(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto<Object>> handleValidationException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        final StringBuilder builder = new StringBuilder();
        e.getAllErrors().forEach(error -> builder.append(Objects.requireNonNull(error.getDefaultMessage())).append(", "));
        return new ResponseEntity<>(BaseResponseDto.builder().message(builder.toString()).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SecurityException.class, AuthenticationException.class, AccessDeniedException.class})
    public ResponseEntity<BaseResponseDto<Object>> handleSecurityException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(BaseResponseDto.builder().message(e.getMessage()).build(), HttpStatus.UNAUTHORIZED);
    }
}
