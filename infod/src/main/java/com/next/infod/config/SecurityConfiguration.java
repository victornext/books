package com.next.infod.config;

import com.next.infod.security.CustomUserDetailsService;
import com.next.infod.security.LoginSocialSucessHandler;
import com.next.infod.services.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSucessHandler sucessHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Para aplicações web
                .formLogin(Customizer.withDefaults()) // Para ela receber autenticação via login
                .formLogin(configurer ->
                        configurer.loginPage("/login").
                                permitAll()) // Formulário padrão
                .authorizeHttpRequests(authorizer -> {
                    authorizer.requestMatchers("/login/**").permitAll();
                    authorizer.requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll();
                    authorizer.anyRequest().authenticated(); // Para toda requisição nessa API tem que estar autenticado
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                    .loginPage("/login")
                    .successHandler(sucessHandler);
                }) // OAuth2 login
                .build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }


}
