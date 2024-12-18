package com.next.infod.controller;


import com.next.infod.controller.DTOS.UsuarioDTO;
import com.next.infod.controller.mappers.UsuarioMapper;
import com.next.infod.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;
    private final UsuarioMapper mapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UsuarioDTO dto) {
        var usuario = mapper.toEntity(dto);
        service.create(usuario);
    }
}
