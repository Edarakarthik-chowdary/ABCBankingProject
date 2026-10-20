package com.karthik.banking_backend.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karthik.banking_backend.dto.DashboardResponse;
import com.karthik.banking_backend.entity.Account;
import com.karthik.banking_backend.entity.Transaction;
import com.karthik.banking_backend.entity.User;
import com.karthik.banking_backend.repository.AccountRepository;
import com.karthik.banking_backend.repository.TransactionRepository;
import com.karthik.banking_backend.repository.UserRepository;
import com.karthik.banking_backend.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public DashboardResponse getDashboard(String email) {

        // Get Logged-in User
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get User Accounts
        List<Account> accounts = accountRepository.findByUser(user);

        if (accounts.isEmpty()) {
            throw new RuntimeException("No Account Found");
        }

        BigDecimal totalBalance = BigDecimal.ZERO;
        BigDecimal totalDeposit = BigDecimal.ZERO;
        BigDecimal totalWithdraw = BigDecimal.ZERO;
        BigDecimal totalTransfer = BigDecimal.ZERO;

        long totalTransactions = 0;

        // Loop through all accounts
        for (Account account : accounts) {

            totalBalance = totalBalance.add(account.getBalance());

            List<Transaction> transactions =
                    transactionRepository.findByFromAccountOrToAccount(
                            account.getAccountNumber(),
                            account.getAccountNumber());

            totalTransactions += transactions.size();

            for (Transaction transaction : transactions) {

                String type = transaction.getTransactionType();

                if ("DEPOSIT".equalsIgnoreCase(type)) {

                    totalDeposit = totalDeposit.add(transaction.getAmount());

                } else if ("WITHDRAW".equalsIgnoreCase(type)) {

                    totalWithdraw = totalWithdraw.add(transaction.getAmount());

                } else if ("TRANSFER".equalsIgnoreCase(type)) {

                    totalTransfer = totalTransfer.add(transaction.getAmount());

                }

            }

        }

        // Use first account for dashboard display
        Account firstAccount = accounts.get(0);

        DashboardResponse response = new DashboardResponse();

        response.setFullName(user.getFullName());
        response.setAccountNumber(firstAccount.getAccountNumber());
        response.setAccountType(firstAccount.getAccountType());

        response.setBalance(totalBalance);
        response.setTotalDeposit(totalDeposit);
        response.setTotalWithdraw(totalWithdraw);
        response.setTotalTransfer(totalTransfer);
        response.setTotalTransactions(totalTransactions);

        return response;
    }

}