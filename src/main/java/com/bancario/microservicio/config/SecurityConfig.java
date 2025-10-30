package com.bancario.microservicio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define usuarios en memoria (para pruebas)
    @Bean
    public UserDetailsService userDetailsService() {
        // Usuario con permisos de Administrador (para POST, PUT, DELETE)
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("adminpass"))
                .roles("ADMIN")
                .build();

        // Usuario con permisos de solo lectura
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para APIs REST sin estado
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Endpoints de MODIFICACIÓN: solo rol ADMIN
                        .requestMatchers("POST", "/api/v1/bancos").hasRole("ADMIN")
                        .requestMatchers("PUT", "/api/v1/bancos/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/v1/bancos/**").hasRole("ADMIN")

                        //  Endpoints de CONSULTA: ADMIN o USER
                        .requestMatchers("GET", "/api/v1/bancos/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.init(http));

        return http.build();
    }
}