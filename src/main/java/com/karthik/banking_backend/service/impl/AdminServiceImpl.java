package com.karthik.banking_backend.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karthik.banking_backend.dto.AdminDashboardResponse;
import com.karthik.banking_backend.entity.Account;
import com.karthik.banking_backend.entity.Transaction;
import com.karthik.banking_backend.entity.User;
import com.karthik.banking_backend.repository.AccountRepository;
import com.karthik.banking_backend.repository.TransactionRepository;
import com.karthik.banking_backend.repository.UserRepository;
import com.karthik.banking_backend.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public AdminDashboardResponse getDashboard() {

        AdminDashboardResponse response =
                new AdminDashboardResponse();

        response.setTotalUsers(userRepository.count());

        response.setTotalAccounts(accountRepository.count());

        response.setTotalTransactions(
                transactionRepository.count());

        BigDecimal totalBalance =
                accountRepository.findAll()
                        .stream()
                        .map(Account::getBalance)
                        .reduce(BigDecimal.ZERO,
                                BigDecimal::add);

        response.setTotalBalance(totalBalance);

        return response;

    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    @Override
    public List<Account> getAllAccounts() {

        return accountRepository.findAll();

    }

    @Override
    public List<Transaction> getAllTransactions() {

        return transactionRepository.findAll();

    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);

    }

}