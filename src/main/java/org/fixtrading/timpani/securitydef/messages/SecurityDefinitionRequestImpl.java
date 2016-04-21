package org.fixtrading.timpani.securitydef.messages;

/**
 * POJO for FIX message SecurityDefinitionRequest
 * 
 * @author Don Mendelson
 *
 */
public class SecurityDefinitionRequestImpl implements SecurityDefinitionRequest {

  private final String msgType = "SecurityDefinitionRequest";

  private String cfiCode;
  private String marketID;
  private String marketSegmentID;
  private String product;
  private String securityExchange;
  private String securityGroup;
  private String securityReqID;
  private SecurityRequestType securityRequestType;
  private String securityType;
  private SubscriptionRequestType subscriptionRequestType;
  private String symbol;
  private String tradingSessionID;
  private String tradingSessionSubID;
  
  @Override
  public String getCfiCode() {
    return cfiCode;
  }
  @Override
  public String getMarketID() {
    return marketID;
  }

  @Override
  public String getMarketSegmentID() {
    return marketSegmentID;
  }

  @Override
  public String getMsgType() {
    return msgType;
  }

  @Override
  public String getProduct() {
    return product;
  }

  @Override
  public String getSecurityExchange() {
    return securityExchange;
  }

  public String getSecurityGroup() {
    return securityGroup;
  }

  @Override
  public String getSecurityReqID() {
    return securityReqID;
  }

  @Override
  public SecurityRequestType getSecurityRequestType() {
    return securityRequestType;
  }

  @Override
  public String getSecurityType() {
    return securityType;
  }

  @Override
  public SubscriptionRequestType getSubscriptionRequestType() {
    return subscriptionRequestType;
  }

  @Override
  public String getSymbol() {
    return symbol;
  }

  @Override
  public String getTradingSessionID() {
    return tradingSessionID;
  }

  @Override
  public String getTradingSessionSubID() {
    return tradingSessionSubID;
  }

  @Override
  public void setCfiCode(String cfiCode) {
    this.cfiCode = cfiCode;
  }

  public void setMarketID(String marketID) {
    this.marketID = marketID;
  }

  public void setMarketSegmentID(String marketSegmentID) {
    this.marketSegmentID = marketSegmentID;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public void setSecurityExchange(String securityExchange) {
    this.securityExchange = securityExchange;
  }

  public void setSecurityGroup(String securityGroup) {
    this.securityGroup = securityGroup;
  }

  public void setSecurityReqID(String securityReqID) {
    this.securityReqID = securityReqID;
  }

  public void setSecurityRequestType(SecurityRequestType securityRequestType) {
    this.securityRequestType = securityRequestType;
  }

  public void setSecurityType(String securityType) {
    this.securityType = securityType;
  }

  public void setSubscriptionRequestType(SubscriptionRequestType subscriptionRequestType) {
    this.subscriptionRequestType = subscriptionRequestType;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setTradingSessionID(String tradingSessionID) {
    this.tradingSessionID = tradingSessionID;
  }
  public void setTradingSessionSubID(String tradingSessionSubID) {
    this.tradingSessionSubID = tradingSessionSubID;
  }

}
