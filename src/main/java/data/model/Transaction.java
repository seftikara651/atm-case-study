package main.java.data.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import main.java.data.constant.TransactionType;

public class Transaction {
    private String id;
    private LocalDateTime timestamp;
    private Customer customer;
    private TransactionType type;
    private BigDecimal amount;

    public Transaction(String id, LocalDateTime timestamp, Customer customer, TransactionType type, BigDecimal amount) {
        this.id = id;
        this.timestamp = timestamp;
        this.customer = customer;
        this.type = type;
        this.amount = amount;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
