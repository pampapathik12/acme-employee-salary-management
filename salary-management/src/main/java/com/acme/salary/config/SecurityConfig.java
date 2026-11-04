package com.acme.salary.config;

import com.acme.salary.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // Swagger access
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()

                        // Public endpoints
                        .requestMatchers(
                                "/",
                                "/health",
                                "/api/auth/**",
                                "/error"
                        ).permitAll()

                        // Role-based access
                        .requestMatchers("/api/employees/**").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/api/departments/**").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/api/designations/**").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/api/attendance/**").hasAnyRole("ADMIN", "HR", "EMPLOYEE")
                        .requestMatchers("/api/leaves/**").hasAnyRole("ADMIN", "HR", "EMPLOYEE")
                        .requestMatchers("/api/payroll/**").hasAnyRole("ADMIN", "HR")
                        .requestMatchers("/api/salaries/**").hasAnyRole("ADMIN", "HR")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}