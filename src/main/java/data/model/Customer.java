package data.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  private String id;

  private String account;
  private String pin;
  private String fullName;
  private BigDecimal balance;
  private Integer invalidTries;

  public String getId() {
    return id;
  }

  public String getAccount() {
    return account;
  }

  public String getPin() {
    return pin;
  }

  public String getFullName() {
    return fullName;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public Integer getInvalidTries() {
    return invalidTries;
  }
  /**
   * Use this function to add balance to Customer
   *
   * @param amount
   */
  public void add(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }


}
