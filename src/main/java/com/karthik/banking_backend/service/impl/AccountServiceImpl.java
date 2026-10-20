package com.karthik.banking_backend.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karthik.banking_backend.dto.AccountRequest;
import com.karthik.banking_backend.dto.DepositRequest;
import com.karthik.banking_backend.dto.TransferRequest;
import com.karthik.banking_backend.dto.WithdrawRequest;
import com.karthik.banking_backend.entity.Account;
import com.karthik.banking_backend.entity.Transaction;
import com.karthik.banking_backend.entity.User;
import com.karthik.banking_backend.repository.AccountRepository;
import com.karthik.banking_backend.repository.TransactionRepository;
import com.karthik.banking_backend.repository.UserRepository;
import com.karthik.banking_backend.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // ===========================
    // Create Account
    // ===========================
    @Override
    public Account createAccount(String email, AccountRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();

        account.setAccountNumber("ACC" + System.currentTimeMillis());
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus("ACTIVE");
        account.setCreatedDate(LocalDateTime.now());
        account.setUser(user);

        return accountRepository.save(account);
    }

    // ===========================
    // Deposit
    // ===========================
    @Override
    public Account deposit(DepositRequest request) {

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid Amount");
        }

        account.setBalance(account.getBalance().add(request.getAmount()));

        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(account.getAccountNumber());
        transaction.setToAccount(account.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return account;
    }

    // ===========================
    // Withdraw
    // ===========================
    @Override
    public Account withdraw(WithdrawRequest request) {

        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid Amount");
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));

        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(account.getAccountNumber());
        transaction.setToAccount(account.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("WITHDRAW");
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return account;
    }

    // ===========================
    // Transfer
    // ===========================
    @Override
    public Account transfer(TransferRequest request) {

        Account sender = accountRepository.findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> new RuntimeException("Sender Account not found"));

        Account receiver = accountRepository.findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new RuntimeException("Receiver Account not found"));

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid Amount");
        }

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(sender.getAccountNumber());
        transaction.setToAccount(receiver.getAccountNumber());
        transaction.setAmount(request.getAmount());
        transaction.setTransactionType("TRANSFER");
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return sender;
    }

    // ===========================
    // Get Account
    // ===========================
    @Override
    public Account getAccount(String accountNumber) {

        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    // ===========================
    // Transaction History
    // ===========================
    @Override
    public List<Transaction> getTransactions(String accountNumber) {

        return transactionRepository.findByFromAccountOrToAccount(
                accountNumber,
                accountNumber);

    }

}