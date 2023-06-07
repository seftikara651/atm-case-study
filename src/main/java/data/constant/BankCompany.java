package data.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BankCompany {
//  public static final String BRI = "BRI";
//  public static final String BNI = "BNI";
//  public static final String MANDIRI = "Mandiri";
//  public static final String BCA = "BCA";
  BRI("BRI"),
  BNI("BNI"),
  MANDIRI("Mandiri"),
  BCA("BCA"),

  ;

  private final String name;

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

//  public static int[] values() {
//    return new int[]{11};
//  }


}
