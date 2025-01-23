package com.next.infod.controller.DTOS;


import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(int status, String message, List<ErrorResponse> errors) {

    public static ErrorResponse respostaPadrao(String message) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ErrorResponse conflito(String message){
        return new ErrorResponse((HttpStatus.CONFLICT.value()), message, List.of());
    }

    public static ErrorResponse errosDeValidacao(String message, List<ErrorResponse> errosDetalhados) {
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), message, errosDetalhados);
    }
}
