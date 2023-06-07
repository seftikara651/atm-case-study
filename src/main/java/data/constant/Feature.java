package data.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Feature {
  VIEW_BALANCE("1"),
  WITHDRAW("2"),
  TOP_UP_PHONE_CREDIT("3"),
  TOP_UP_ELECTRICITY_BILL("4"),
  UNIVERSITY_PAYMENT("5"),
  ACCOUNT_MUTATION("6"),
  DEPOSIT("7"),
  ;

  private final String code;

  public static Feature fromCode(String code) {
    for (Feature feature : Feature.values()) {
      if (feature.code.equals(code)) {
        return feature;
      }
    }
    return null;
  }
}
