package com.next.infod.controller;


import com.next.infod.controller.DTOS.ClientDTO;
import com.next.infod.controller.mappers.ClientMapper;
import com.next.infod.model.Client;
import com.next.infod.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody ClientDTO DTO) {
        Client client = mapper.toEntity(DTO);

        log.info("Registrando novo client: {} com scope: {} ", client.getClientId(), client.getScope());
        service.salvar(client);
    }
}
