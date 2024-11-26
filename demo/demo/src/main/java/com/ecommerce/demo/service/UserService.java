package com.ecommerce.demo.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.demo.dto.UserProfileDTO;
import com.ecommerce.demo.dto.UserUpdateDTO;
import com.ecommerce.demo.entity.Provider;
import com.ecommerce.demo.entity.User;
import com.ecommerce.demo.entity.UserRole;
import com.ecommerce.demo.exception.ResourceNotFoundException;
import com.ecommerce.demo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User processOAuthPostLogin(String email, String name, Provider provider, String providerId) {
        Optional<User> existUser = userRepository.findByEmail(email);
        
        if (existUser.isPresent()) {
            User user = existUser.get();
            if (provider != user.getProvider()) {
                user.setProvider(provider);
                user.setProviderId(providerId);
                return userRepository.save(user);
            }
            return user;
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstName(name);
            newUser.setProvider(provider);
            newUser.setProviderId(providerId);
            newUser.setUserRole(UserRole.USER);
            return userRepository.save(newUser);
        }
    }
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile(Integer id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return convertToProfileDTO(user);
    }

    @Transactional
    public UserProfileDTO updateUser(Integer id, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Update only if values are provided
        if (updateDTO.getFirstName() != null) {
            user.setFirstName(updateDTO.getFirstName());
        }
        if (updateDTO.getLastName() != null) {
            user.setLastName(updateDTO.getLastName());
        }
        if (updateDTO.getEmail() != null) {
            validateEmail(updateDTO.getEmail(), id);
            user.setEmail(updateDTO.getEmail());
        }
        if (updateDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(updateDTO.getPhoneNumber());
        }

        User savedUser = userRepository.save(user);
        return convertToProfileDTO(savedUser);
    }

    private void validateEmail(String email, Integer userId) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent() && existingUser.get().getUserId() != userId) {
            throw new IllegalArgumentException("Email already in use");
        }
    }

    private UserProfileDTO convertToProfileDTO(User user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}