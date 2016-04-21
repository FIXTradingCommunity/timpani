package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * Type of settlement price
 * datatype int
 * @author Don Mendelson
 *
 */
public enum SettlPriceType {
  Final(1), Theoretical(2);

  private static final EnumSet<SettlPriceType> valueSet = EnumSet.allOf(SettlPriceType.class);

  public static SettlPriceType getValue(int code) {
    Iterator<SettlPriceType> iter = valueSet.iterator();
    while (iter.hasNext()) {
      SettlPriceType value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private final int code;

  SettlPriceType(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
