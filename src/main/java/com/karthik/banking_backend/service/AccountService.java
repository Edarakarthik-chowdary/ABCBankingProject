package com.karthik.banking_backend.service;

import java.util.List;

import com.karthik.banking_backend.dto.AccountRequest;
import com.karthik.banking_backend.dto.DepositRequest;
import com.karthik.banking_backend.dto.TransferRequest;
import com.karthik.banking_backend.dto.WithdrawRequest;
import com.karthik.banking_backend.entity.Account;
import com.karthik.banking_backend.entity.Transaction;

public interface AccountService {

    Account createAccount(String email, AccountRequest request);

    Account deposit(DepositRequest request);

    Account withdraw(WithdrawRequest request);

    Account transfer(TransferRequest request);

    Account getAccount(String accountNumber);

    List<Transaction> getTransactions(String accountNumber);

}