package com.next.infod.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

    @Bean
    public TokenSettings tokenSettings(){
        return tokenSettings().builder()
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .accessTokenTimeToLive(Duration.ofMinutes(60)) //Pra todos os clients o token vai durar esse tempo
                .build();
    }

    @Bean
    public ClientSettings clientSettings(){
        return clientSettings().builder()
                .requireAuthorizationConsent(false) //Quando a gente vai se autenticar com google, a tela de consentimento de dados do google (geralmente email e tals)
                .build();
    }
}
