package org.fixtrading.timpani.securitydef.messages;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * POJO for FIX message SecurityDefinition
 * 
 * @author Don Mendelson
 */
public class SecurityDefinitionImpl implements SecurityDefinition {

  public class InstrAttribImpl implements InstrAttrib {

    private InstrAttribType instrAttribType;
    private String instrAttribValue;

    @Override
    public InstrAttribType getInstrAttribType() {
      return instrAttribType;
    }

    @Override
    public String getInstrAttribValue() {
      return instrAttribValue;
    }

    public void setInstrAttribType(InstrAttribType instrAttribType) {
      this.instrAttribType = instrAttribType;
    }

    public void setInstrAttribValue(String instrAttribValue) {
      this.instrAttribValue = instrAttribValue;
    }
  }

  public class MarketSegmentImpl implements MarketSegment {

    private String marketID;
    private String marketSegmentID;


    @Override
    public String getMarketID() {
      return marketID;
    }

    @Override
    public String getMarketSegmentID() {
      return marketSegmentID;
    }


    public void setMarketID(String marketID) {
      this.marketID = marketID;
    }

    public void setMarketSegmentID(String marketSegmentID) {
      this.marketSegmentID = marketSegmentID;
    }
  }

  private final String msgType = "SecurityDefinition";

  private String cfiCode;
  private double contractMultiplier;
  private String currency;
  private final ArrayList<InstrAttribImpl> instrAttribGrp = new ArrayList<>();
  private LocalDateTime lastUpdateTime;
  private final ArrayList<MarketSegmentImpl> marketSegmentGrp = new ArrayList<>();
  private String matchAlgorithm;
  private String MaturityMonthYear;
  private int maxTradeVol;
  private double minPriceIncrement;
  private Product product;
  private PutOrCall putOrCall;
  private String SecurityDesc;
  private String securityExchange;
  private String securityGroup;
  private String SecurityID;
  private SecurityIDSource securityIDSource;
  private SecurityRejectReason securityRejectReason;
  private String securityReqID;
  private SecurityRequestResult securityRequestResult;
  private SecurityResponseType securityResponseType;
  private String securityType;
  private String settlCurrency;
  private SettlPriceType settlPriceType;
  private double strikePrice;
  private SubscriptionRequestType subscriptionRequestType;
  private String symbol;
  private String tradingSessionID;
  private String tradingSessionSubID;
  private Product underlyingProduct;

  public InstrAttribImpl addInstrAttrib() {
    InstrAttribImpl instrAttribImpl = new InstrAttribImpl();
    instrAttribGrp.add(instrAttribImpl);
    return instrAttribImpl;
  }

  public MarketSegmentImpl addMarketSegment() {
    MarketSegmentImpl marketSegmentImpl = new MarketSegmentImpl();
    marketSegmentGrp.add(marketSegmentImpl);
    return marketSegmentImpl;
  }

  @Override
  public String getCfiCode() {
    return cfiCode;
  }

  @Override
  public double getContractMultiplier() {
    return this.contractMultiplier;
  }

  @Override
  public String getCurrency() {
    return this.currency;
  }

  @Override
  public InstrAttrib getInstrAttrib(int index) {
    return instrAttribGrp.get(index);
  }

  @Override
  public LocalDateTime getLastUpdateTime() {
    return this.lastUpdateTime;
  }

  @Override
  public MarketSegment getMarketSegment(int index) {
    return marketSegmentGrp.get(index);
  }

  @Override
  public String getMatchAlgorithm() {
    return this.matchAlgorithm;
  }

  @Override
  public String getMaturityMonthYear() {
    return MaturityMonthYear;
  }

  @Override
  public int getMaxTradeVol() {
    return this.maxTradeVol;
  }

  @Override
  public double getMinPriceIncrement() {
    return this.minPriceIncrement;
  }

  @Override
  public String getMsgType() {
    return msgType;
  }

  @Override
  public int getNoInstrAttrib() {
    return instrAttribGrp.size();
  }

  @Override
  public int getNoMarketSegments() {
    return marketSegmentGrp.size();
  }

  @Override
  public Product getProduct() {
    return product;
  }

  @Override
  public PutOrCall getPutOrCall() {
    return putOrCall;
  }

  @Override
  public String getSecurityDesc() {
    return SecurityDesc;
  }

  @Override
  public String getSecurityExchange() {
    return this.securityExchange;
  }

  @Override
  public String getSecurityGroup() {
    return this.securityGroup;
  }

  @Override
  public String getSecurityID() {
    return SecurityID;
  }

  @Override
  public SecurityIDSource getSecurityIDSource() {
    return securityIDSource;
  }

  @Override
  public SecurityRejectReason getSecurityRejectReason() {
    return securityRejectReason;
  }

  @Override
  public String getSecurityReqID() {
    return securityReqID;
  }

  @Override
  public SecurityRequestResult getSecurityRequestResult() {
    return securityRequestResult;
  }

  @Override
  public SecurityResponseType getSecurityResponseType() {
    return securityResponseType;
  }

  @Override
  public String getSecurityType() {
    return securityType;
  }

  @Override
  public String getSettlCurrency() {
    return this.settlCurrency;
  }

  @Override
  public SettlPriceType getSettlPriceType() {
    return this.settlPriceType;
  }

  @Override
  public double getStrikePrice() {
    return strikePrice;
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
  public Product getUnderlyingProduct() {
    return this.underlyingProduct;
  }

  public void setCfiCode(String cfiCode) {
    this.cfiCode = cfiCode;
  }

  public void setContractMultiplier(double contractMultiplier) {
    this.contractMultiplier = contractMultiplier;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void setLastUpdateTime(LocalDateTime zonedDateTime) {
    this.lastUpdateTime = zonedDateTime;
  }

  public void setMatchAlgorithm(String matchAlgorithm) {
    this.matchAlgorithm = matchAlgorithm;
  }

  public void setMaturityMonthYear(String maturityMonthYear) {
    MaturityMonthYear = maturityMonthYear;
  }

  public void setMaxTradeVol(int maxTradeVol) {
    this.maxTradeVol = maxTradeVol;
  }

  public void setMinPriceIncrement(double minPriceIncrement) {
    this.minPriceIncrement = minPriceIncrement;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public void setPutOrCall(PutOrCall putOrCall) {
    this.putOrCall = putOrCall;
  }

  public void setSecurityDesc(String securityDesc) {
    SecurityDesc = securityDesc;
  }

  public void setSecurityExchange(String securityExchange) {
    this.securityExchange = securityExchange;
  }

  public void setSecurityGroup(String securityGroup) {
    this.securityGroup = securityGroup;
  }

  public void setSecurityID(String securityID) {
    SecurityID = securityID;
  }

  public void setSecurityIDSource(SecurityIDSource securityIDSource) {
    this.securityIDSource = securityIDSource;
  }

  public void setSecurityRejectReason(SecurityRejectReason securityRejectReason) {
    this.securityRejectReason = securityRejectReason;
  }

  public void setSecurityReqID(String securityReqID) {
    this.securityReqID = securityReqID;
  }

  public void setSecurityRequestResult(SecurityRequestResult securityRequestResult) {
    this.securityRequestResult = securityRequestResult;
  }

  public void setSecurityResponseType(SecurityResponseType securityResponseType) {
    this.securityResponseType = securityResponseType;
  }

  public void setSecurityType(String securityType) {
    this.securityType = securityType;
  }

  public void setSettlCurrency(String settlCurrency) {
    this.settlCurrency = settlCurrency;
  }

  public void setSettlPriceType(SettlPriceType settlPriceType) {
    this.settlPriceType = settlPriceType;
  }

  public void setStrikePrice(double strikePrice) {
    this.strikePrice = strikePrice;
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

  public void setUnderlyingProduct(Product underlyingProduct) {
    this.underlyingProduct = underlyingProduct;
  }
}
