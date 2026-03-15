package com.example.auth_server.user.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
}
