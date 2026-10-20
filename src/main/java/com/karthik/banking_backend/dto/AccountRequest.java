package com.karthik.banking_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class AccountRequest {

    @NotBlank(message = "Account Type is required")
    private String accountType;

    public AccountRequest() {
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}