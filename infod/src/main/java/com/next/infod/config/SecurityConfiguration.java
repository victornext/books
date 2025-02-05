package com.next.infod.config;

import com.next.infod.security.CustomUserDetailsService;
import com.next.infod.security.JWTCustomAuthenticationFilter;
import com.next.infod.security.LoginSocialSucessHandler;
import com.next.infod.services.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   LoginSocialSucessHandler
                                                           sucessHandler,
                                                   JWTCustomAuthenticationFilter jwtCustomAuthenticationFilter
                                                   ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Para aplicações web
                .formLogin(Customizer.withDefaults()) // Para ela receber autenticação via login
                .formLogin(configurer ->
                        configurer.loginPage("/login").permitAll()) // Formulário padrão
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
                .oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()))
                .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                    "/v2/api-docs/**",
                    "/v3/api-docs/**",
                    "/swagger=resources/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/actuator/**"

            );
        }



    //Configura o prefixo ROLE que vem do banco de dados por exemplo ROLE_GERENTE
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    //Configura no token JWT o prefixo SCOPE
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix(""); //Por padrão e SCOPE_

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }

}

