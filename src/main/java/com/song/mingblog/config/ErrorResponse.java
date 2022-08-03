package com.song.mingblog.config;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

// 에러처리 메세지 -> code : 400, message : "잘못된 요청입니다."
@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation = new HashMap<>();

    public void addValidation(String fieldName, String errorMessage){
        this.validation.put(fieldName, errorMessage);
    }

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
