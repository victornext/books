package com.next.infod.controller.commom;

import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.exceptions.Illegal;
import com.next.infod.exceptions.LivroNaoEncontrado;
import com.next.infod.exceptions.NaoAutorizadaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
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
}
