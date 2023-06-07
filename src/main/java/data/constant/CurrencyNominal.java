package data.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum CurrencyNominal {
  M10K,
  M20K,
  M50K,
  M100K,

  ;

  public static Object getAllNominals() {
    return "code selanjutnya";
  }

  public BigDecimal getValue() {
//    code menyusul
    return BigDecimal.valueOf(22.3);
  }
}
