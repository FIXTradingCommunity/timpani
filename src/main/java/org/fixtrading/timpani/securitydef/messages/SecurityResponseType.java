package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * Type of Security Definition message response datatype int
 * 
 * @author Don Mendelson
 *
 */
public enum SecurityResponseType {
  AcceptAsIs(1), AcceptWithRevisions(2), @Deprecated ListOfSecurityTypesReturnedPerRequest(
      3), ListOfSecuritiesReturnedPerRequest(
          4), RejectSecurityProposal(5), CannotMatchSelectionCriteria(6);

  private static final EnumSet<SecurityResponseType> valueSet = EnumSet.allOf(SecurityResponseType.class);

  public static SecurityResponseType getValue(int code) {
    Iterator<SecurityResponseType> iter = valueSet.iterator();
    while (iter.hasNext()) {
      SecurityResponseType value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private final int code;

  SecurityResponseType(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
