package com.next.infod.controller.commom;

import com.next.infod.controller.DTOS.ErrorResponse;
import com.next.infod.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Tratamento para ArquivoDuplicado
    @ExceptionHandler(ArquivoDuplicado.class)
    public ResponseEntity<?> handleArquivoDuplicado(ArquivoDuplicado e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status: ", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message:", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    // Tratamento para Illegal
    @ExceptionHandler(Illegal.class)
    public ResponseEntity<?> handleArquivosIlegais(Illegal e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status: ", HttpStatus.BAD_REQUEST.value());  // Usando 400 em vez de 409
        body.put("error", "Bad Request");
        body.put("message:", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Tratamento para NaoAutorizadaException
    @ExceptionHandler(NaoAutorizadaException.class)
    public ResponseEntity<?> handlerNãoAutorizada(NaoAutorizadaException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status: ", HttpStatus.FORBIDDEN.value());
        body.put("error", "Forbidden");
        body.put("message:", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    // Tratamento para LivroNaoEncontrado
    @ExceptionHandler(LivroNaoEncontrado.class)
    public ResponseEntity<?> handlerNãoEncontrado(LivroNaoEncontrado e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status: ", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message:", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Tratamento para validação de parâmetros
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Erro de validação: {}", ex.getMessage());
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Retorna uma resposta com os erros de validação
        errors.put("timestamp", LocalDateTime.now());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Tratamento para erros genéricos (500 - Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e) {
        log.error("Erro inesperado: ", e.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", e.getMessage()); // Mensagem da exceção
        body.put("details", "An unexpected error occurred. Please contact support if the problem persists.");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CampoInvalido.class)
    public ResponseEntity<?> CampoInvalido(CampoInvalido e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status: ", HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error", "Campo preço em livros a partir de 2020 é obrigatorio!");
        body.put("message:", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccesDeniedException(AccessDeniedException e){
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Acesso Negado.", List.of());
    }


}
