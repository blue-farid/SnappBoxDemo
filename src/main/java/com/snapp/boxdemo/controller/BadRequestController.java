package com.snapp.boxdemo.controller;

import com.snapp.boxdemo.model.dto.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * custom bad request controller
 */
@RestController
@RequiredArgsConstructor
public class BadRequestController implements ErrorController {
    private final MessageSource source;

    @RequestMapping("/error")
    public ResponseEntity<BaseResponseDto<Object>> error() {
        return new ResponseEntity<>(BaseResponseDto.builder().message(source.getMessage("error.bad.api.call", null,
                Locale.getDefault())).build(), HttpStatus.BAD_REQUEST);
    }
}
