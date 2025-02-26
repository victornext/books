package com.next.infod.services;

import com.next.infod.controller.mappers.UsuarioMapper;
import com.next.infod.model.Usuario;
import com.next.infod.repositories.UsuarioRepository;
import com.next.infod.validator.UsuarioValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;
    private final UsuarioValidator validator;

    public void create(Usuario usuario){
        validator.validar(usuario);
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return repository.findByLogin(login);
    }

    public Usuario obterPorEmail(String email) {
        return repository.findByEmail(email);
    }
}
