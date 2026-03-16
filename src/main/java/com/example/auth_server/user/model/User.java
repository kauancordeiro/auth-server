package com.example.auth_server.user.model;

import com.example.auth_server.user.dto.CreateUserRequest;
import com.example.auth_server.user.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(CreateUserRequest request, String passEncoded) {
        this.setName(request.getName());
        this.setEmail(request.getEmail());
        this.setPassword(passEncoded);
        this.setRole(Role.USER);
    }
}
