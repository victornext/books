package com.next.infod.services;

import com.next.infod.controller.mappers.UsuarioMapper;
import com.next.infod.model.Usuario;
import com.next.infod.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void create(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return repository.findByLogin(login);
    }
}
