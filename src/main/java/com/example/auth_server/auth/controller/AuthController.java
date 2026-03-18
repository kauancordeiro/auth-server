package com.example.auth_server.auth.controller;

import com.example.auth_server.auth.dto.AuthResponse;
import com.example.auth_server.auth.dto.LoginRequest;
import com.example.auth_server.auth.dto.RefreshRequest;
import com.example.auth_server.auth.service.AuthService;
import com.example.auth_server.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshRequest request) {
        return refreshTokenService.refresh(request);
    }
}
