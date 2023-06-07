package main.java.data.model;

import java.math.BigDecimal;


public class Customer {
  private String id;

  private String account;
  private String pin;

  private String fullName;

  public BigDecimal getBalance() {
	return balance;
}

public void setBalance(BigDecimal balance) {
	this.balance = balance;
}

private BigDecimal balance;

  private Integer invalidTries;

  /**
   * Use this function to add balance to Customer
   *
   * @param amount
   */
  public void add(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }
  public void subtract(BigDecimal amount) {
	    this.balance = this.balance.subtract(amount);
	  }

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getAccount() {
	return account;
}

public void setAccount(String account) {
	this.account = account;
}

public String getPin() {
	return pin;
}

public void setPin(String pin) {
	this.pin = pin;
}

public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}

public Integer getInvalidTries() {
	return invalidTries;
}

public void setInvalidTries(Integer invalidTries) {
	this.invalidTries = invalidTries;
}
}
