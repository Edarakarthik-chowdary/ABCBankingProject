package com.karthik.banking_backend.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.karthik.banking_backend.entity.Account;
import com.karthik.banking_backend.entity.Transaction;
import com.karthik.banking_backend.repository.AccountRepository;
import com.karthik.banking_backend.repository.TransactionRepository;
import com.karthik.banking_backend.service.PdfService;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public byte[] generateStatement(String accountNumber) {

        try {

            Account account = accountRepository
                    .findByAccountNumber(accountNumber)
                    .orElseThrow(() ->
                            new RuntimeException("Account not found"));

            List<Transaction> transactions =
                    transactionRepository.findByFromAccountOrToAccount(
                            accountNumber,
                            accountNumber);

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font titleFont = new Font(
                    Font.FontFamily.HELVETICA,
                    18,
                    Font.BOLD);

            Font headingFont = new Font(
                    Font.FontFamily.HELVETICA,
                    12,
                    Font.BOLD);

            document.add(new Paragraph(
                    "ABC BANK",
                    titleFont));

            document.add(new Paragraph("Bank Statement"));
            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "Customer Name : "
                            + account.getUser().getFullName(),
                    headingFont));

            document.add(new Paragraph(
                    "Account Number : "
                            + account.getAccountNumber()));

            document.add(new Paragraph(
                    "Account Type : "
                            + account.getAccountType()));

            document.add(new Paragraph(
                    "Current Balance : ₹"
                            + account.getBalance()));

            document.add(new Paragraph(
                    "Status : "
                            + account.getStatus()));

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);

            table.setWidthPercentage(100);

            table.addCell("Date");
            table.addCell("Type");
            table.addCell("From");
            table.addCell("To");
            table.addCell("Amount");

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy HH:mm");

            for (Transaction transaction : transactions) {

                table.addCell(
                        transaction.getTransactionDate()
                                .format(formatter));

                table.addCell(
                        transaction.getTransactionType());

                table.addCell(
                        transaction.getFromAccount());

                table.addCell(
                        transaction.getToAccount());

                table.addCell(
                        "₹" + transaction.getAmount());

            }

            document.add(table);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to Generate PDF");

        }

    }

}