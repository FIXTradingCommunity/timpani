package org.fixtrading.timpani.securitydef.messages;

import java.time.LocalDateTime;

/**
 * Interface for FIX SecurityDefinition
 * 
 * @author Don Mendelson
 * @see <a href="http://www.fixtradingcommunity.org/pg/resources/fiximate">Message type = 'd'</a>
 */
public interface SecurityDefinition extends Message {

  interface InstrAttrib {
    // tag 871
    InstrAttribType getInstrAttribType();

    // tag 872
    String getInstrAttribValue();
  }
  
  interface MarketSegment {

    // tag 1301
    String getMarketID();

    // tag 1300
    String getMarketSegmentID();
  }

  // tag 870
  int getNoInstrAttrib();

  InstrAttrib getInstrAttrib(int index);
  
  // tag 1310
  int getNoMarketSegments();

  MarketSegment getMarketSegment(int index);
  
  // tag 461
  String getCfiCode();

  // tag 231
  double getContractMultiplier();

  // tag 15
  String getCurrency();

  // tag 779
  LocalDateTime getLastUpdateTime();

  // tag 1142
  String getMatchAlgorithm();

  // tag 200
  String getMaturityMonthYear();

  // tag 1140
  int getMaxTradeVol();

  // tag 969
  double getMinPriceIncrement();

  // tag 460 - int
  Product getProduct();

  // tag 201 - int
  PutOrCall getPutOrCall();

  // tag 107
  String getSecurityDesc();

  // tag 207
  String getSecurityExchange();

  // tag 1151
  String getSecurityGroup();

  // tag 48
  String getSecurityID();

  // tag 22
  SecurityIDSource getSecurityIDSource();

  // tag 1607
  SecurityRejectReason getSecurityRejectReason();

  // tag 320
  String getSecurityReqID();

  // tag 560
  SecurityRequestResult getSecurityRequestResult();

  // tag 323
  SecurityResponseType getSecurityResponseType();

  // tag 167
  String getSecurityType();

  // tag 120
  String getSettlCurrency();

  // tag 731 - int
  SettlPriceType getSettlPriceType();

  // tag 202
  double getStrikePrice();

  // tag 23
  SubscriptionRequestType getSubscriptionRequestType();

  // tag 55
  String getSymbol();

  // tag 336
  String getTradingSessionID();

  // tag 625
  String getTradingSessionSubID();

  // tag 462 - int
  Product getUnderlyingProduct();


}
