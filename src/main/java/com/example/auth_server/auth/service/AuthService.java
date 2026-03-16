package com.example.auth_server.auth.service;

import com.example.auth_server.auth.dto.LoginRequest;
import com.example.auth_server.auth.dto.LoginResponse;
import com.example.auth_server.security.JwtService;
import com.example.auth_server.user.model.User;
import com.example.auth_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        validatePassword(request.getPassword(), user.getPassword());

        String token = jwtService.generateToken(user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    private void validatePassword(String actual, String expected) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(actual, expected)) {
            throw new RuntimeException("Invalid password");
        }
    }

}
