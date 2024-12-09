package com.next.infod.controller.DTOS;

import java.util.List;

public record UsuarioDTO(
        String login,
        String senha,
        List<String> roles
) {

}
