package com.acme.salary.service;

import com.acme.salary.dto.LoginRequest;
import com.acme.salary.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    void logout(String token);

}
