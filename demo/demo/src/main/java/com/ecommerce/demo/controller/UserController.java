package com.ecommerce.demo.controller;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.demo.entity.User;
import com.ecommerce.demo.entity.UserRole;
import com.ecommerce.demo.dto.RegisterRequest;
import com.ecommerce.demo.service.UserService;
import lombok.Data;

@RestController
@RequestMapping("/api/auth")  // Changed from /usercontroller to follow REST conventions
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint is working!");
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        System.out.println("Register request received: " + request.getUsername());

        if (userService.existsByUsername(request.getUsername())) {
            System.out.println("Username already exists: " + request.getUsername());
            return ResponseEntity.badRequest()
                    .body("Username already exists");
        }

        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());
            user.setUserRole(UserRole.USER);
            user.setCreatedAt(LocalDateTime.now());

            User savedUser = userService.save(user);
            System.out.println("User registered successfully: " + savedUser.getUsername());
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering user: " + e.getMessage());
        }
    }

    @Data
    private static class LoginRequest {

        private String username;
        private String password;
    }
}
