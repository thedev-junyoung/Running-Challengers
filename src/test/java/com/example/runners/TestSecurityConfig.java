package com.example.runners;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain disableCsrfFilterChain(HttpSecurity http) throws Exception {
        // token-based authentication settings
        http.csrf((auth) -> auth.disable());
        http.formLogin((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());

        http
                .authorizeRequests()
                .anyRequest().permitAll();

        return http.build();
    }
}
