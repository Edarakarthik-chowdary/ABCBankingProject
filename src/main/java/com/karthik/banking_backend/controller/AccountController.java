package com.karthik.banking_backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.karthik.banking_backend.dto.AccountRequest;
import com.karthik.banking_backend.dto.DepositRequest;
import com.karthik.banking_backend.dto.TransferRequest;
import com.karthik.banking_backend.dto.WithdrawRequest;
import com.karthik.banking_backend.entity.Account;
import com.karthik.banking_backend.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public Account createAccount(
            @Valid @RequestBody AccountRequest request,
            Principal principal) {

        return accountService.createAccount(
                principal.getName(),
                request);
    }

    @PostMapping("/deposit")
    public Account deposit(
            @Valid @RequestBody DepositRequest request) {

        return accountService.deposit(request);
    }

    @PostMapping("/withdraw")
    public Account withdraw(
            @Valid @RequestBody WithdrawRequest request) {

        return accountService.withdraw(request);
    }

    @PostMapping("/transfer")
    public Account transfer(
            @Valid @RequestBody TransferRequest request) {

        return accountService.transfer(request);
    }

}