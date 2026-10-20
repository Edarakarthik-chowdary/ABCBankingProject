package com.karthik.banking_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.karthik.banking_backend.dto.AdminDashboardResponse;
import com.karthik.banking_backend.entity.Account;
import com.karthik.banking_backend.entity.Transaction;
import com.karthik.banking_backend.entity.User;
import com.karthik.banking_backend.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ===========================
    // Admin Dashboard
    // ===========================
    @GetMapping("/dashboard")
    public AdminDashboardResponse getDashboard() {

        return adminService.getDashboard();

    }

    // ===========================
    // Get All Users
    // ===========================
    @GetMapping("/users")
    public List<User> getAllUsers() {

        return adminService.getAllUsers();

    }

    // ===========================
    // Get All Accounts
    // ===========================
    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {

        return adminService.getAllAccounts();

    }

    // ===========================
    // Get All Transactions
    // ===========================
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {

        return adminService.getAllTransactions();

    }

    // ===========================
    // Delete User
    // ===========================
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {

        adminService.deleteUser(id);

        return "User Deleted Successfully";

    }

}