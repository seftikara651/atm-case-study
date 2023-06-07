package data.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public enum BankCompany {
  BRI("BRI"),
  BNI("BNI"),
  MANDIRI("Mandiri"),
  BCA("BCA");

  private String name;

  public String getName() {
    return name;
  }

  public static BankCompany getByOrder(int order) {
    switch (order) {
      case 0:
        return BRI;
      case 1:
        return BNI;
      case 2:
        return MANDIRI;
      case 3:
        return BCA;
      default:
        throw new IllegalArgumentException("Cannot find BankCompany of order " + order);
    }
  }
}
