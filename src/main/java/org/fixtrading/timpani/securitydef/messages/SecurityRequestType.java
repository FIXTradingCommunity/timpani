package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * Type of Security Definition Request datatype int
 * 
 * @author Don Mendelson
 *
 */
public enum SecurityRequestType {
  RequestSecurityIdentityAndSpecifications(0), 
  @Deprecated RequestSecurityIdentityForSpecifications(1),
  @Deprecated RequestListSecurityTypes(2),
  RequestListSecurities(3),
  Symbol(4),
  SecurityTypeAndOrCFICode(5),
  Product(6),
  TradingSessionID(7),
  AllSecurities(8),
  MarketIDOrMarketID(9);
  
  private static EnumSet<SecurityRequestType> valueSet = EnumSet.allOf(SecurityRequestType.class);

  public static SecurityRequestType getValue(int code) {
    Iterator<SecurityRequestType> iter = valueSet.iterator();
    while (iter.hasNext()) {
      SecurityRequestType value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private final int code;

  private SecurityRequestType(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
