package com.next.infod.repositories;

import com.next.infod.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Usuario findByLogin(String login);

}