package org.fixtrading.timpani.securitydef.messages;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * Identifies class or source of the SecurityID value datatype char
 * 
 * @author Don Mendelson
 *
 */
public enum SecurityIDSource {
  CUSIP('1'), SEDOL('2'), QUIK('3'), ISINNumber('4'), RICCode('5'), ISOCurrencyCode(
      '6'), ISOCountryCode('7'), ExchangeSymbol('8'), ConsolidatedTapeAssociation(
          '9'), BloombergSymbol('A'), Wertpapier('B'), Dutch('C'), Valoren('D'), Sicovam(
              'E'), Belgian('F'), Common('G'), ClearingHouse(
                  'H'), ISDAFpMLSpecification('I'), OptionPriceReportingAuthority('J'), ISDAFpMLURL(
                      'K'), LetterOfCredit('L'), MarketplaceAssignedIdentifier(
                          'M'), MarkitREDEntityCLIP('N'), MarkitREDPairCLIP('P'), CFTCCommodityCode(
                              'Q'), ISDACommodityReferencePrice(
                                  'R'), FinancialInstrumentGlobalIdentifier(
                                      'S'), LegalEntityIdentifier('T'), Synthetic('U');

  private static final EnumSet<SecurityIDSource> valueSet = EnumSet.allOf(SecurityIDSource.class);

  public static SecurityIDSource getValue(char code) {
    Iterator<SecurityIDSource> iter = valueSet.iterator();
    while (iter.hasNext()) {
      SecurityIDSource value = iter.next();
      if (value.getCode() == code) {
        return value;
      }
    }
    return null;
  }

  private final char code;

  SecurityIDSource(char code) {
    this.code = code;
  }

  public char getCode() {
    return code;
  }
}
