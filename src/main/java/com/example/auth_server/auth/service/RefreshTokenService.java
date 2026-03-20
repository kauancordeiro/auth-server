package com.example.auth_server.auth.service;

import com.example.auth_server.auth.dto.AuthResponse;
import com.example.auth_server.auth.dto.RefreshRequest;
import com.example.auth_server.auth.entity.RefreshToken;
import com.example.auth_server.auth.repository.RefreshTokenRepository;
import com.example.auth_server.security.JwtService;
import com.example.auth_server.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private static final int DAYS_TO_EXPIRE = 7;
    private final RefreshTokenRepository repository;
    private final JwtService jwtService;

    public RefreshToken createRefreshToken(User user) {

        RefreshToken token = new RefreshToken(user, DAYS_TO_EXPIRE);

        return repository.save(token);
    }

    public RefreshToken validate(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getRevoked()) {
            throw new RuntimeException("Token already used");
        }

        if (refreshToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }
        return refreshToken;
    }

    public AuthResponse refresh(RefreshRequest request) {

        RefreshToken oldToken = validate(request.getRefreshToken());
        oldToken.setRevoked(true);
        repository.save(oldToken);

        RefreshToken newToken = createRefreshToken(oldToken.getUser());
        String newAccessToken = jwtService.generateToken(oldToken.getUser().getEmail());

        return AuthResponse.builder()
                .token(newAccessToken)
                .refreshToken(newToken.getToken())
                .build();

    }

    public void logout(RefreshRequest request) {

        RefreshToken refreshToken = validate(request.getRefreshToken());

        User user = refreshToken.getUser();

        List<RefreshToken> tokens = repository.findAllByUserAndRevokedFalse(user);

        revokeAllTokens(tokens);

        repository.saveAll(tokens);
    }

    private static void revokeAllTokens(List<RefreshToken> tokens) {
        for (RefreshToken token : tokens) {
            token.setRevoked(true);
        }
    }

}
