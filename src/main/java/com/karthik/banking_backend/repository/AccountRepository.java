package com.karthik.banking_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karthik.banking_backend.entity.Account;
import com.karthik.banking_backend.entity.User;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // Find by account number
    Optional<Account> findByAccountNumber(String accountNumber);

    // Find account by user
    List<Account> findByUser(User user);

}