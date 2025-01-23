package com.next.infod.controller.mappers;

import com.next.infod.controller.DTOS.ClientDTO;
import com.next.infod.model.BooksModel;
import com.next.infod.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface  ClientMapper {
    Client toEntity(ClientDTO dto);

    ClientDTO toDTO(Client client);
}
