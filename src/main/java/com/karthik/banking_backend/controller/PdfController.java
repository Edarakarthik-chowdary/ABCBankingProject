package com.karthik.banking_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.karthik.banking_backend.service.PdfService;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/statement/{accountNumber}")
    public ResponseEntity<byte[]> downloadStatement(
            @PathVariable String accountNumber) {

        byte[] pdf = pdfService.generateStatement(accountNumber);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_PDF);

        headers.setContentDisposition(
                ContentDisposition
                        .attachment()
                        .filename("Statement_" + accountNumber + ".pdf")
                        .build());

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdf);
    }

}