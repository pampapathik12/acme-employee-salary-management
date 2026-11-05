package com.acme.salary.controller;

import com.acme.salary.dto.LoginRequest;
import com.acme.salary.dto.LoginResponse;
import com.acme.salary.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Sign in and obtain a JWT token")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Log in")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out and revoke the current JWT")
    public ResponseEntity<Void> logout(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        authService.logout(authorizationHeader.substring(7));
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
