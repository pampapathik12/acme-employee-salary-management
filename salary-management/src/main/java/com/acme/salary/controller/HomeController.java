package com.acme.salary.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {

        return Map.of(
                "application", "Salary Management System",
                "message", "Welcome! Application is running successfully.",
                "status", "UP"
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {

        return Map.of(
                "status", "UP",
                "message", "Salary Management System is healthy"
        );
    }
}