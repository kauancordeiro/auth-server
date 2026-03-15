package com.example.auth_server.user.service;

import com.example.auth_server.user.dto.CreateUserRequest;
import com.example.auth_server.user.dto.UserResponse;
import com.example.auth_server.user.model.User;
import com.example.auth_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(CreateUserRequest request) {
        validateEmail(request.getEmail());
        User user = new User(request);
        user = userRepository.save(user);
        return new UserResponse(user);
    }

    private void validateEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
    }

}
