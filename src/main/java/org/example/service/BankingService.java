package org.example.service;

import org.example.repositories.AccountRepository;
import org.example.repositories.TransactionRepository;
import org.example.models.Account;
import org.example.models.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankingService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BankingService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(Account account) {
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public Transaction performTransaction(String accountNumber, BigDecimal amount, Transaction.TransactionType type) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (type == Transaction.TransactionType.WITHDRAWAL && account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        if (type == Transaction.TransactionType.WITHDRAWAL) {
            account.setBalance(account.getBalance().subtract(amount));
        } else {
            account.setBalance(account.getBalance().add(amount));
        }

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
        accountRepository.save(account);

        return transaction;
    }

    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
