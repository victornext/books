package com.next.infod.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)//Para aplicações web
                .formLogin(Customizer.withDefaults()) //Para ela receber autenticação via login
                .formLogin(configurer ->
                        configurer.loginPage("/login").permitAll()) //Formulario padrao
                .httpBasic(Customizer.withDefaults()) //
                .authorizeHttpRequests(authorizer ->{
                    authorizer.anyRequest().authenticated();  //Para toda requisicao nessa API tem que estar autenticado
                })
                .build();

    }

}
