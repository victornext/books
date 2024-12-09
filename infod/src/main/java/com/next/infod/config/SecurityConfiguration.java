package com.next.infod.config;


import com.next.infod.security.CustomUserDetailsService;
import com.next.infod.services.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.beans.Encoder;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)//Para aplicações web
                .formLogin(Customizer.withDefaults()) //Para ela receber autenticação via login
                .formLogin(configurer ->
                        configurer.loginPage("/login").
                                permitAll()) //Formulario padrao
                .httpBasic(Customizer.withDefaults()) //
                .authorizeHttpRequests(authorizer ->{
                    authorizer.requestMatchers("/login/**").permitAll();
                    authorizer.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();

                    authorizer.anyRequest().authenticated();  //Para toda requisicao nessa API tem que estar autenticado
                })
                .oauth2Login(Customizer.withDefaults())
                .build();

    }

    @Bean
    public PasswordEncoder  passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        return new CustomUserDetailsService(usuarioService);
    }
}
