package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/*
 * Identifies the reason a security definition request is being rejected. datatype int
 * 
 * @author Don Mendelson
 */
public enum SecurityRejectReason {
  InvalidInstrumentRequested(1), InstrumentAlreadyExists(2), RequestTypeNotSupported(
      3), SystemUnavailableForInstrumentCreation(4), IneligibleInstrumentGroup(
          5), InstrumentIDUnavailable(6), InvalidOrMissingDataOnOptionLeg(
              7), InvalidOrMissingDataOnFutureLeg(8), InvalidOrMissingDataOnFXLeg(
                  10), InvalidLegPriceSpecified(11), InvalidInstrumentStructureSpecified(12);

  private static final EnumSet<SecurityRejectReason> valueSet = EnumSet.allOf(SecurityRejectReason.class);

  public static SecurityRejectReason getValue(int code) {
    Iterator<SecurityRejectReason> iter = valueSet.iterator();
    while (iter.hasNext()) {
      SecurityRejectReason value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private final int code;

  SecurityRejectReason(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
