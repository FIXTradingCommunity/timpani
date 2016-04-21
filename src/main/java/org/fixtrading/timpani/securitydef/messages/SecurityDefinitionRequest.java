package org.fixtrading.timpani.securitydef.messages;



public interface SecurityDefinitionRequest extends Message {

  String getCfiCode();

  String getMarketID();

  String getMarketSegmentID();

  String getProduct();

  // tag 207
  String getSecurityExchange();
  
  // tag 1151
  String getSecurityGroup();
  
  String getSecurityReqID();

  SecurityRequestType getSecurityRequestType();

  String getSecurityType();

  SubscriptionRequestType getSubscriptionRequestType();

  String getSymbol();

  String getTradingSessionID();

  String getTradingSessionSubID();

  void setCfiCode(String cfiCode);

}
