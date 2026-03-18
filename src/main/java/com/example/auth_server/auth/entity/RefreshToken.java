package com.example.auth_server.auth.entity;

import com.example.auth_server.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    private LocalDateTime expirationDate;

    @ManyToOne
    private User user;


    public RefreshToken(User user, int daysToExpire) {
        this.setUser(user);
        this.setToken(UUID.randomUUID().toString());
        this.setExpirationDate(LocalDateTime.now().plusDays(daysToExpire));
    }
}
