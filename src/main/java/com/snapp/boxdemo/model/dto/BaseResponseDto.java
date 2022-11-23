package com.snapp.boxdemo.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BaseResponseDto<T> implements Serializable {
    private String message;
    private T result;
}
