package com.next.infod.validator;

import com.next.infod.exceptions.ArquivoDuplicado;
import com.next.infod.exceptions.Illegal;
import com.next.infod.model.Usuario;
import com.next.infod.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UsuarioValidator {

    @Autowired
    private final UsuarioRepository repository;

    public void validar(Usuario usuario){
        dadosiguais(usuario);
        if(existeUsuarioPorEmail(usuario) || existeUsuarioPorLogin(usuario)) {
            throw new ArquivoDuplicado("Dados já cadastrados!!!");
        }
    }

    public void dadosiguais(Usuario usuario) {
        Optional<Usuario> findingForEmail = repository.findingEmail(
                usuario.getEmail()
        );

        Optional<Usuario> findingForLogin = repository.findingLogin(
                usuario.getLogin()
        );


        if(findingForEmail.isPresent()) {
            Usuario existeUsuario = findingForEmail.get();

            if (usuario.getEmail().equals(findingForEmail.get().getEmail())) {
                throw new Illegal("Email já registrado");
            }

        }



        if(findingForLogin.isPresent()) {
            Usuario existeUsuario = findingForLogin.get();

            if (usuario.getLogin().equals(findingForLogin.get().getLogin())) {
                throw new Illegal("Login já registrado");
            }

        }
    }

    private boolean existeUsuarioPorLogin(Usuario usuario) {
        Optional<Usuario> findingForLogin = repository.findingLogin(usuario.getLogin());
        if (usuario.getLogin() != null) {
            return findingForLogin.isPresent();
        }
        return findingForLogin.isPresent() && !usuario.getLogin().equals(findingForLogin.get().getLogin());

    }

    private boolean existeUsuarioPorEmail(Usuario usuario) {
        Optional<Usuario> findingForEmail = repository.findingEmail(usuario.getEmail());
        if (usuario.getEmail() != null) {

            return findingForEmail.isPresent();
        }
        return findingForEmail.isPresent() && !usuario.getEmail().equals(findingForEmail.get().getEmail());

    }


}
