package data.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import data.constant.TransactionType;

public class Transaction {
  private String id;

  private LocalDateTime timestamp;

  private Customer customer;
  private TransactionType type;

  private BigDecimal expense;

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }
}

