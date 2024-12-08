package org.example.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID") // Maps to TransactionID in the DB
    private Long id;

    @ManyToOne
    @JoinColumn(name = "AccountID", nullable = false) // Foreign key to Accounts(AccountID)
    @JsonBackReference
    private Account account;

    @Column(name = "Amount", nullable = false) // Maps to Amount
    private BigDecimal amount;

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "transactiontype", nullable = false) // Maps to TransactionType
    private TransactionType transactionType;

    @Column(name = "transactiondate", nullable = false) // Maps to TransactionDate
    private LocalDateTime timestamp;

//    // No-args constructor
//    public Transaction() {}
//
//    // All-args constructor
//    public Transaction(Long id, Account account, BigDecimal amount, TransactionType transactionType, LocalDateTime timestamp) {
//        this.id = id;
//        this.account = account;
//        this.amount = amount;
//        this.transactionType = transactionType;
//        this.timestamp = timestamp;
//    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
