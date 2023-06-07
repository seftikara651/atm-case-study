package main.java.data.constant;

public enum BankCompany {
  BRI,
  BNI,
  MANDIRI,
  BCA,

  ;

  private String name;

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

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
}
