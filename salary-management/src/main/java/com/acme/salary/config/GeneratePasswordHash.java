package com.acme.salary.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeneratePasswordHash {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String password = "Admin@123";

        String encodedPassword = encoder.encode(password);

        System.out.println("BCrypt Password: " + encodedPassword);
    }
}
