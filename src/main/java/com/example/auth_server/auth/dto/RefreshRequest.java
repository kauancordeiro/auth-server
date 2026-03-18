package com.example.auth_server.auth.dto;

import lombok.Data;

@Data
public class RefreshRequest {

    private String refreshToken;

}