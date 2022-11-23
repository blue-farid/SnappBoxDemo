package com.snapp.boxdemo.model.dto;

import lombok.Builder;
@Builder
public class BaseResponseDto<T> {
    private String message;
    private T result;
}
