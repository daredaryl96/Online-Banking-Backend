package org.example.controller;

import org.example.models.Account;
import org.example.models.Transaction;
import org.example.models.TransactionRequest;
import org.example.service.BankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/banking")
public class BankingController {
    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    // Create a new account
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = bankingService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    // Get account details by account number
    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Account> getAccountByAccountNumber(@PathVariable String accountNumber) {
        Account account = bankingService.getAccountByAccountNumber(accountNumber);
        return ResponseEntity.ok(account);
    }

    // Perform a transaction (deposit or withdrawal)
    @PostMapping("/accounts/{accountNumber}/transactions")
    public ResponseEntity<Transaction> performTransaction(
            @PathVariable String accountNumber,
            @RequestBody TransactionRequest transactionRequest) {
        if (!accountNumber.equals(transactionRequest.getAccountNumber())) {
            throw new IllegalArgumentException("Account number mismatch between path and request body");
        }

        Transaction.TransactionType type = Transaction.TransactionType.valueOf(transactionRequest.getTransactionType());
        Transaction transaction = bankingService.performTransaction(
                accountNumber,
                transactionRequest.getAmount(),
                type
        );
        return ResponseEntity.ok(transaction);
    }

    // Get transaction history for an account
    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long accountId) {
        List<Transaction> transactions = bankingService.getTransactionHistory(accountId);
        return ResponseEntity.ok(transactions);
    }
}
