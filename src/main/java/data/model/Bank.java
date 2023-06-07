package main.java.data.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Bank {
  private String id;

  private String name;

  private Boolean depositFeature;

  private BigDecimal maxExpensePerWithdrawal;
  private BigDecimal maxExpensePerUserDaily;


  private Set<Customer> customers = new HashSet<>();

  public Boolean getDepositFeature() {
	return depositFeature;
}

public void setDepositFeature(Boolean depositFeature) {
	this.depositFeature = depositFeature;
}

public Set<Customer> getCustomers() {
	return customers;
}

public void setCustomers(Set<Customer> customers) {
	this.customers = customers;
}

public Set<Transaction> getTransactions() {
	return transactions;
}

public void setTransactions(Set<Transaction> transactions) {
	this.transactions = transactions;
}

private Set<Transaction> transactions = new HashSet<>();

  public boolean hasDepositFeature() {
    return this.depositFeature.booleanValue();
  }

  public Optional<Customer> findCustomerByAccount(String account) {
    return customers.stream().filter(item -> account.equals(item.getAccount())).findAny();
  }

  public Set<Transaction> findAllTransactionsByAccount(String account) {
    return transactions.stream().filter(item -> account.equals(item.getCustomer().getAccount()))
        .collect(Collectors.toSet());
  }

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public BigDecimal getMaxExpensePerWithdrawal() {
	return maxExpensePerWithdrawal;
}

public void setMaxExpensePerWithdrawal(BigDecimal maxExpensePerWithdrawal) {
	this.maxExpensePerWithdrawal = maxExpensePerWithdrawal;
}

public BigDecimal getMaxExpensePerUserDaily() {
	return maxExpensePerUserDaily;
}

public void setMaxExpensePerUserDaily(BigDecimal maxExpensePerUserDaily) {
	this.maxExpensePerUserDaily = maxExpensePerUserDaily;
}
}
