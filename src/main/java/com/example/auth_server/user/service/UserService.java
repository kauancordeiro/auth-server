package com.example.auth_server.user.service;

import com.example.auth_server.user.dto.CreateUserRequest;
import com.example.auth_server.user.dto.UserResponse;
import com.example.auth_server.user.model.User;
import com.example.auth_server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(CreateUserRequest request) {
        validateEmail(request.getEmail());
        String passEncoded = encodePass(request.getPassword());
        User user = new User(request, passEncoded);
        user = userRepository.save(user);
        return new UserResponse(user);
    }

    private String encodePass(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
    }

    public List<UserResponse> getUsers() {
        List<User> userList = userRepository.findAll();
        return UserResponse.toDtoList(userList);
    }
}
