package com.next.infod.controller.mappers;


import com.next.infod.controller.DTOS.UsuarioDTO;
import com.next.infod.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}