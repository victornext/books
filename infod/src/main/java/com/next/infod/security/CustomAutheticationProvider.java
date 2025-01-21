package com.next.infod.security;

import com.next.infod.model.Usuario;
import com.next.infod.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAutheticationProvider implements AuthenticationProvider {
    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuarioEncontrado = usuarioService.obterPorLogin(login);

        if(usuarioEncontrado == null) {
            throw new UsernameNotFoundException("Usuario e/ou senha incorretos!");
        }

        String senhaCriptografa = usuarioEncontrado.getSenha();

        Boolean senhasBatem = encoder.matches(senhaDigitada, senhaCriptografa);

        if(senhasBatem) {
            return new CustomAuthentication(usuarioEncontrado);
        }

        throw new UsernameNotFoundException("Usuario e/ou senha incorretos!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
