package com.next.infod.controller.commom;



import com.next.infod.exceptions.ArquivoDuplicado;

import com.next.infod.exceptions.Illegal;
import com.next.infod.exceptions.LivroNaoEncontrado;
import com.next.infod.exceptions.NaoAutorizadaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArquivoDuplicado.class)
    public ResponseEntity<?> handleArquivoDuplicado(ArquivoDuplicado e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status: ", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message:", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Illegal.class)
    public ResponseEntity<?> handleArquivosIlegais(Illegal e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status: ", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message:", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NaoAutorizadaException.class)
    public ResponseEntity<?> handlerNãoAutorizada(NaoAutorizadaException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status: ", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message:", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(LivroNaoEncontrado.class)
    public ResponseEntity<?> handlerNãoEncontrado(LivroNaoEncontrado e){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp: ", LocalDateTime.now());
        body.put("status: ", HttpStatus.NOT_FOUND.value());
        body.put("error", "Não encontrado");
        body.put("message:", e.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}

