package com.karthik.banking_backend.service;

import java.util.List;

import com.karthik.banking_backend.dto.AdminDashboardResponse;
import com.karthik.banking_backend.entity.Account;
import com.karthik.banking_backend.entity.Transaction;
import com.karthik.banking_backend.entity.User;

public interface AdminService {

    AdminDashboardResponse getDashboard();

    List<User> getAllUsers();

    List<Account> getAllAccounts();

    List<Transaction> getAllTransactions();

    void deleteUser(Long id);

}