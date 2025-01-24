package com.next.infod.controller.mappers;

import com.next.infod.controller.DTOS.UsuarioDTO;
import com.next.infod.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T23:41:09-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Azul Systems, Inc.)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setLogin( dto.login() );
        usuario.setEmail( dto.email() );
        usuario.setSenha( dto.senha() );
        List<String> list = dto.roles();
        if ( list != null ) {
            usuario.setRoles( new ArrayList<String>( list ) );
        }

        return usuario;
    }
}
