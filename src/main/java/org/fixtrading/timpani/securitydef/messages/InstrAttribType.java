package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * Code to represent the type of instrument attribute datatype int
 * 
 * @author Don Mendelson
 *
 */
public enum InstrAttribType {

  Callable(11), CallableBelowMaturityValue(21), CallableWithoutNotice(22), CouponPeriod(
      8), DummyInstrument(35), EscrowedToMaturity(12), EscrowedToRedemptionDate(13), Flat(
          1), InDefault(15), Indexed(18), InstrumentDenominator(25), InstrumentEligibleAnonOrders(
              30), InstrumentNumerator(26), InstrumentPricePrecision(27), InstrumentStrikePrice(
                  28), InterestBearing(3), LessFeeForPut(6), MinGuaranteedFillStatus(
                      32), MinGuaranteedFillVolume(31), NegativeSettlementPriceEligibility(
                          36), NegativeStrikePriceEligibility(37), NoPeriodicPayments(
                              4), OriginalIssueDiscount(10), OriginalIssueDiscountPrice(
                                  20), PreRefunded(14), PriceTickRulesForSecurity(
                                      23), SteppedCoupon(7), SubjectToAlternativeMinimumTax(
                                          19), Taxable(17), TestInstrument(34), Text(
                                              99), TradeableIndicator(
                                                  29), TradeAtSettlementEligibility(
                                                      33), TradeTypeEligibilityDetailsForSecurity(
                                                          24), Unrated(16), USStdContractInd(
                                                              38), VariableRate(
                                                                  5), When(9), ZeroCoupon(2);

  private static final EnumSet<InstrAttribType> valueSet = EnumSet.allOf(InstrAttribType.class);

  public static InstrAttribType getValue(int code) {
    Iterator<InstrAttribType> iter = valueSet.iterator();
    while (iter.hasNext()) {
      InstrAttribType value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private final int code;

  InstrAttribType(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

}
