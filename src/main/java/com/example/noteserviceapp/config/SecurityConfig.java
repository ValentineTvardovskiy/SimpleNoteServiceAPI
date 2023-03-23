package com.example.noteserviceapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/notes/*/like").authenticated()
                        .requestMatchers("/notes/*/unlike").authenticated()
                        .requestMatchers("/notes/**").permitAll()
                )
                .httpBasic();
        return security.build();
    }
}
