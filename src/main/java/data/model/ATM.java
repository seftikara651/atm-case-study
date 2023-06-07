package main.java.data.model;

import java.math.BigDecimal;

public class ATM {
  private String id;

  public BigDecimal getBalance() {
	return balance;
}

public void setBalance(BigDecimal balance) {
	this.balance = balance;
}

private BigDecimal balance;

  private Bank bank;

  /**
   * Use this function to subtract balance to ATM
   *
   * @param amount
   */
  public void subtract(BigDecimal amount) {
    this.balance = this.balance.subtract(amount);
  }

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public Bank getBank() {
	return bank;
}

public void setBank(Bank bank) {
	this.bank = bank;
}

public void add(BigDecimal amount) {
	this.balance = this.balance.add(amount);
	
}
}
