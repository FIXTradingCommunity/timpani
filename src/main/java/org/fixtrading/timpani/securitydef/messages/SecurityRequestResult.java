package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * The results returned to a Security Request message datatype char
 * 
 * @author Don Mendelson
 *
 */
public enum SecurityRequestResult {
  ValidRequest(0), InvalidOrUnsupportedRequest(1), NoInstrumentsFound(
      2), NotAuthorizedToRetrieveInstrumentData(
          3), InstrumentDataTemporarilyUnavailable(4), RequestForInstrumentDataNotSupported(5);

  private static EnumSet<SecurityRequestResult> valueSet =
      EnumSet.allOf(SecurityRequestResult.class);

  public static SecurityRequestResult getValue(int code) {
    Iterator<SecurityRequestResult> iter = valueSet.iterator();
    while (iter.hasNext()) {
      SecurityRequestResult value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private final int code;

  private SecurityRequestResult(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
