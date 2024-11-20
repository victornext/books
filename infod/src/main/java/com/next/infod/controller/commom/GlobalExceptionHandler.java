package com.next.infod.controller.commom;


import com.next.infod.DTOS.ErroDTO;
import com.next.infod.DTOS.RespostadeErro;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespostadeErro handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroDTO> listaErros = fieldErrors.stream()
                .map(fe -> new ErroDTO(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new RespostadeErro(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", listaErros);
    }


}

