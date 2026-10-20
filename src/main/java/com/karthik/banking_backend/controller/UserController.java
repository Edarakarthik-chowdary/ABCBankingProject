package com.karthik.banking_backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.karthik.banking_backend.dto.LoginRequest;
import com.karthik.banking_backend.dto.LoginResponse;
import com.karthik.banking_backend.dto.UpdateProfileRequest;
import com.karthik.banking_backend.entity.User;
import com.karthik.banking_backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ===========================
    // Register User
    // ===========================
    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody User user) {

        return userService.registerUser(user);
    }

    // ===========================
    // Login User
    // ===========================
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return userService.login(request);
    }

    // ===========================
    // Get User By ID
    // ===========================
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    // ===========================
    // Get All Users
    // ===========================
    @GetMapping
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    // ===========================
    // Get Logged-in User Profile
    // ===========================
    @GetMapping("/profile")
    public User getProfile(Principal principal) {

        return userService.getProfile(principal.getName());
    }

    // ===========================
    // Update Logged-in User Profile
    // ===========================
    @PutMapping("/profile")
    public User updateProfile(@RequestBody UpdateProfileRequest request,
                              Principal principal) {

        return userService.updateProfile(principal.getName(), request);
    }

}