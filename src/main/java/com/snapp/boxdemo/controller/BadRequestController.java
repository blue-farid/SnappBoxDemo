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
    public ResponseEntity<BaseResponseDto<Object>> error(Locale locale) {
        return new ResponseEntity<>(BaseResponseDto.builder().message(source.getMessage("error.badApiCall", null,
                locale)).build(), HttpStatus.BAD_REQUEST);
    }
}
