package com.acme.salary.service.impl;

import com.acme.salary.dto.LoginRequest;
import com.acme.salary.dto.LoginResponse;
import com.acme.salary.entity.User;
import com.acme.salary.repository.UserRepository;
import com.acme.salary.service.AuthService;
import com.acme.salary.security.JwtService;
import com.acme.salary.security.TokenBlacklist;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenBlacklist tokenBlacklist;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            TokenBlacklist tokenBlacklist) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid email or password"));

        if (!Boolean.TRUE.equals(user.getActive())
                || !Boolean.TRUE.equals(user.getEnabled())) {
            throw new DisabledException("User account is inactive");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (user.getRole() == null) {
            throw new IllegalStateException("User role is not assigned");
        }

        String role = user.getRole().getRoleName();

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                token,
                "Bearer",
                user.getEmail(),
                role
        );
    }

    @Override
    public void logout(String token) {
        tokenBlacklist.revoke(token, jwtService.extractExpiration(token));
    }
}
