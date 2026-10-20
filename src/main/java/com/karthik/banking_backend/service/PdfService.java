package com.karthik.banking_backend.service;

public interface PdfService {

    byte[] generateStatement(String accountNumber);

}