package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * Subscription Request Type datatype char
 * 
 * @author Don Mendelson
 *
 */
public enum SubscriptionRequestType {
  Snapshot('0'), SnapshotAndUpdates('1'), DisablePreviousSnapshot('2');

  private static EnumSet<SubscriptionRequestType> valueSet =
      EnumSet.allOf(SubscriptionRequestType.class);

  public static SubscriptionRequestType getValue(char code) {
    Iterator<SubscriptionRequestType> iter = valueSet.iterator();
    while (iter.hasNext()) {
      SubscriptionRequestType value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private char code;

  private SubscriptionRequestType(char code) {
    this.code = code;
  }

  public char getCode() {
    return code;
  }

}
