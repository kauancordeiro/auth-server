package com.example.auth_server.user.dto;

import com.example.auth_server.user.model.User;
import com.example.auth_server.user.model.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;

    public UserResponse(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setRole(user.getRole());
    }

    public static List<UserResponse> toDtoList(List<User> entityList) {
        return entityList
                .stream()
                .map(UserResponse::new)
                .toList();
    }
}
