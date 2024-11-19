package com.next.infod.DTOS;

import org.aspectj.lang.reflect.DeclareErrorOrWarning;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public record ErrorResponse(int status, String message, List<ErrorResponse> errors) {

    public static ErrorResponse respostaPadrao(String message) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ErrorResponse conflito(String message){
        return new ErrorResponse((HttpStatus.CONFLICT.value()), message, List.of());
    }
}
