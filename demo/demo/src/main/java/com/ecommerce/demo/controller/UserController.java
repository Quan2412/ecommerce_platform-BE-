package com.ecommerce.demo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.demo.dto.FacebookLoginRequest;
import com.ecommerce.demo.dto.GoogleLoginRequest;
import com.ecommerce.demo.dto.LoginRequest;
import com.ecommerce.demo.dto.RegisterRequest;
import com.ecommerce.demo.dto.UserDTO;
import com.ecommerce.demo.entity.Provider;
import com.ecommerce.demo.entity.User;
import com.ecommerce.demo.service.JwtService;
import com.ecommerce.demo.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@RestController
@RequestMapping("/auth/api")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService = null;
    private final NetHttpTransport httpTransport;
    private final GsonFactory gsonFactory;

    public UserController(
            UserService userService,
            PasswordEncoder passwordEncoder,
            NetHttpTransport httpTransport,
            GsonFactory gsonFactory) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.httpTransport = httpTransport;
        this.gsonFactory = gsonFactory;
    }

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${facebook.app.id}")
    private String facebookAppId;

    @Value("${facebook.app.secret}")
    private String facebookAppSecret;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth endpoint is working!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> userOpt = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                String token = jwtService.generateToken(user);
                
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("user", convertToDTO(user));
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("message", "Invalid username or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Login error: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            if (userService.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "Email này đã được sử dụng"));
            }

            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setProvider(Provider.LOCAL);

            User savedUser = userService.save(user);
            String token = jwtService.generateToken(savedUser);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Đăng ký thành công!");
            response.put("token", token);
            response.put("user", convertToDTO(savedUser));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Registration error: " + e.getMessage()));
        }
    }


    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getToken());
            if (idToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("message", "Token Google không hợp lệ"));
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            // Process user info
            User user = userService.processOAuthPostLogin(
                    payload.getEmail(),
                    (String) payload.get("name"),
                    Provider.GOOGLE,
                    payload.getSubject()
            );

            String token = jwtService.generateToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Đăng nhập Google thất bại: " + e.getMessage()));
        }
    }

    @PostMapping("/facebook-login")
    public ResponseEntity<?> facebookLogin(@RequestBody FacebookLoginRequest request) {
        try {
            // Validate Facebook token
            String fbValidationUrl = String.format(
                    "https://graph.facebook.com/debug_token?input_token=%s&access_token=%s|%s",
                    request.getAccessToken(),
                    facebookAppId,
                    facebookAppSecret
            );

            // Process the user
            User user = userService.processOAuthPostLogin(
                    request.getEmail(),
                    request.getName(),
                    Provider.FACEBOOK,
                    request.getUserID()
            );

            // Generate JWT token
            String token = jwtService.generateToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", convertToDTO(user));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Đăng nhập Facebook thất bại: " + e.getMessage()));
        }
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId((long) user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getFirstName());
        dto.setProvider(user.getProvider().toString());
        return dto;
    }

}
