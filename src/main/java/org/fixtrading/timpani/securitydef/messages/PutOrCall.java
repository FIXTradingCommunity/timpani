package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * Option type
 * datatype int
 * @author Don Mendelson
 *
 */
public enum PutOrCall {
  Call(1), Put(0);

  private static EnumSet<PutOrCall> valueSet = EnumSet.allOf(PutOrCall.class);

  public static PutOrCall getValue(int code) {
    Iterator<PutOrCall> iter = valueSet.iterator();
    while (iter.hasNext()) {
      PutOrCall value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private final int code;

  private PutOrCall(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
