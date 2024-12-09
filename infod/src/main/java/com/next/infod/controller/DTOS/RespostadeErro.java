package com.next.infod.controller.DTOS;

import org.springframework.http.HttpStatus;

import java.util.List;

public record RespostadeErro(int status, String message, List<ErroDTO> errors) {

    public static RespostadeErro errosDeValidacao(String message, List<ErroDTO> errosDetalhados) {
        return new RespostadeErro(HttpStatus.UNPROCESSABLE_ENTITY.value(), message, errosDetalhados);
    }
}
