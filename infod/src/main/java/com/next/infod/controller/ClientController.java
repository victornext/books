package com.next.infod.controller;


import com.next.infod.controller.DTOS.ClientDTO;
import com.next.infod.controller.mappers.ClientMapper;
import com.next.infod.model.Client;
import com.next.infod.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody ClientDTO DTO) {
        Client client = mapper.toEntity(DTO);

        service.salvar(client);
    }
}
