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
import org.springframework.http.MediaType;
import java.nio.charset.StandardCharsets;

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
                                "/api/auth/login",
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

                        .requestMatchers("/api/payroll-periods/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN", "PAYROLL_MANAGER", "FINANCE_MANAGER")

                        .requestMatchers("/api/salaries/**")
                        .hasAnyRole("ADMIN", "SUPER_ADMIN", "HR_ADMIN", "HR_MANAGER", "PAYROLL_MANAGER", "FINANCE_MANAGER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, exception) -> {
                            response.setStatus(401);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                            response.getWriter().write("{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Authentication is required or your session has expired. Sign in and try again.\",\"path\":\"" + request.getRequestURI() + "\",\"validationErrors\":{}}");
                        })
                        .accessDeniedHandler((request, response, exception) -> {
                            response.setStatus(403);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                            response.getWriter().write("{\"status\":403,\"error\":\"Forbidden\",\"message\":\"You do not have permission to access this resource.\",\"path\":\"" + request.getRequestURI() + "\",\"validationErrors\":{}}");
                        })
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
