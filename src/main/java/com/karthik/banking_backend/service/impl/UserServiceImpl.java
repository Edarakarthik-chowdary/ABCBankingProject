package com.karthik.banking_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.karthik.banking_backend.dto.LoginRequest;
import com.karthik.banking_backend.dto.LoginResponse;
import com.karthik.banking_backend.dto.UpdateProfileRequest;
import com.karthik.banking_backend.entity.User;
import com.karthik.banking_backend.repository.UserRepository;
import com.karthik.banking_backend.security.JwtService;
import com.karthik.banking_backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // ===========================
    // Register User
    // ===========================
    @Override
    public User registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // ===========================
    // Login User
    // ===========================
    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        // Generate JWT Token
        String token = jwtService.generateToken(user.getEmail());

        // Return Login Response
        return new LoginResponse(
                "Login Successful",
                token,
                user.getRole()
        );
    }

    // ===========================
    // Get User By ID
    // ===========================
    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ===========================
    // Get All Users
    // ===========================
    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    // ===========================
    // Get Logged-in User Profile
    // ===========================
    @Override
    public User getProfile(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ===========================
    // Update User Profile
    // ===========================
    @Override
    public User updateProfile(String email, UpdateProfileRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());

        return userRepository.save(user);
    }

}