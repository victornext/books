package com.next.infod.repositories;

import com.next.infod.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Usuario findByLogin(String login);

    @Query("SELECT u FROM Usuario u WHERE u.login = :login")
    Optional<Usuario> findingLogin(String login);

    Usuario findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findingEmail(String email);

}
