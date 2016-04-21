package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * Product type
 * datatype int
 * @author Don Mendelson
 *
 */
public enum Product {
      AGENCY(1),
      COMMODITY(2),
      CORPORATE(3),
      CURRENCY(4),
      EQUITY(5),
      GOVERNMENT(6),
      INDEX(7),
      LOAN(8),
      MONEYMARKET(9),
      MORTGAGE(10),
      MUNICIPAL(11),
      OTHER(12),
      FINANCING(13);

  private static EnumSet<Product> valueSet = EnumSet.allOf(Product.class);

  public static Product getValue(int code) {
    Iterator<Product> iter = valueSet.iterator();
    while (iter.hasNext()) {
      Product value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private final int code;

  private Product(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
