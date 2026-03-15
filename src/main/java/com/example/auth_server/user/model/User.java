package com.example.auth_server.user.model;

import com.example.auth_server.user.dto.CreateUserRequest;
import com.example.auth_server.user.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data
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

    public User(CreateUserRequest request) {
        this.setName(request.getName());
        this.setEmail(request.getEmail());
        this.setPassword(request.getPassword());
        this.setRole(Role.USER);
    }
}
