package com.karthik.banking_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karthik.banking_backend.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // All transactions where the account is sender or receiver
    List<Transaction> findByFromAccountOrToAccount(String fromAccount, String toAccount);

    // Total transaction count
    long countByFromAccountOrToAccount(String fromAccount, String toAccount);

}