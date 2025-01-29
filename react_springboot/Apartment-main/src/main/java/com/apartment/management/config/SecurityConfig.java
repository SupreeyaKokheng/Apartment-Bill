package com.apartment.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults()) // เปิดใช้งาน CORS
            .csrf().disable() // ปิด CSRF เพื่อให้ REST API ทำงาน
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/**").permitAll() // ✅ อนุญาตทุก API
                .anyRequest().permitAll() // ✅ ให้สิทธิ์ทุก request
            )

            .httpBasic().disable(); // ปิด Basic Auth
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // ✅ อนุญาต Angular
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // ✅ Methods ที่อนุญาต
        configuration.setAllowedHeaders(Arrays.asList("*")); // ✅ อนุญาตทุก Header
        configuration.setAllowCredentials(true); // ✅ อนุญาตให้ใช้ Credentials (สำคัญมาก)
    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // ✅ ใช้กับทุก URL
        return source;
    }
    
    
}
