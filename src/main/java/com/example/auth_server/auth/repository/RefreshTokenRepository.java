package com.example.auth_server.auth.repository;

import com.example.auth_server.auth.entity.RefreshToken;
import com.example.auth_server.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUserAndRevokedFalse(User user);
}
