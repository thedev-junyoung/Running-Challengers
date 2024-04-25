package com.example.runners.config;

import com.example.runners.filters.JWTFilter;
import com.example.runners.filters.LoginFilter;
import com.example.runners.utils.JWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWT jwt;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWT jwt) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwt = jwt;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("filterChain() in SecurityConfig");
        // token-based authentication settings
        http.csrf((auth) -> auth.disable());
        http.formLogin((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());

        // set a role for each pages
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login", "/join").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
        );

        // 특정 필터 앞에 커스텀 필터를 추가
        // Login Filter 전 JWT Filter부터 !
        http.addFilterBefore(new JWTFilter(jwt),
                LoginFilter.class);

        // 지정된 필터의 순서에 커스텀 필터 주입 = 바꿔치기
        // UsernamePasswordAuthenticationFilter 를 LoginFilter로 대체
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwt),
                UsernamePasswordAuthenticationFilter.class);


        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //cors
        http.cors((cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();

                config.setAllowedOrigins(Collections.singletonList("http://localhost:5000"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L);
                config.setExposedHeaders(Collections.singletonList("Authorization"));

                return config;
            }
        })));

        return http.build();
    }

}
