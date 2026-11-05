package com.acme.salary.controller;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@RestController
@Tag(name = "System", description = "Application status endpoints")
public class HomeController {

    @GetMapping("/")
    @Operation(summary = "Get application welcome status")
    public Map<String, String> home() {

        return Map.of(
                "application", "Salary Management System",
                "message", "Welcome! Application is running successfully.",
                "status", "UP"
        );
    }

    @GetMapping("/health")
    @Operation(summary = "Check application health")
    public Map<String, String> health() {

        return Map.of(
                "status", "UP",
                "message", "Salary Management System is healthy"
        );
    }
}
