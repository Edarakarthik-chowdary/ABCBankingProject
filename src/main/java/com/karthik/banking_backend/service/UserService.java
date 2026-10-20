package com.karthik.banking_backend.service;

import java.util.List;

import com.karthik.banking_backend.dto.LoginRequest;
import com.karthik.banking_backend.dto.LoginResponse;
import com.karthik.banking_backend.dto.UpdateProfileRequest;
import com.karthik.banking_backend.entity.User;

public interface UserService {
	 User registerUser(User user);
	 LoginResponse login(LoginRequest request);

	    User getUserById(Long id);

	    List<User> getAllUsers();
	    User getProfile(String email);

	    User updateProfile(String email, UpdateProfileRequest request);
}
