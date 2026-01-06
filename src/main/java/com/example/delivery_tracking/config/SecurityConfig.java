package com.example.delivery_tracking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Отключаем CSRF
                .cors(cors -> cors.configurationSource(corsConfigurationSource))  // Подключаем CORS
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()  // Разрешаем все запросы без авторизации
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())  // Разрешаем доступ ко всем ресурсам (например, Swagger UI)
                );

        return http.build();
    }
}