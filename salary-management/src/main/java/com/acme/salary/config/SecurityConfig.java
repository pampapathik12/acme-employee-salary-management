package com.acme.salary.config;

import com.acme.salary.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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

                        .requestMatchers("/api/employees/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN", "HR_ADMIN", "HR_MANAGER")

                        .requestMatchers("/api/departments/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN", "HR_ADMIN", "HR_MANAGER")

                        .requestMatchers("/api/designations/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN", "HR_ADMIN", "HR_MANAGER")

                        .requestMatchers("/api/attendance/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN", "HR_ADMIN", "HR_MANAGER", "EMPLOYEE")

                        .requestMatchers("/api/leaves/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN", "HR_ADMIN", "HR_MANAGER", "EMPLOYEE")

                        .requestMatchers("/api/payroll/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN", "PAYROLL_MANAGER", "FINANCE_MANAGER")

                        .requestMatchers("/api/salaries/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN", "HR_ADMIN", "HR_MANAGER", "PAYROLL_MANAGER", "FINANCE_MANAGER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public OpenAPI customOpenAPI() {

        String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                );
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
}
