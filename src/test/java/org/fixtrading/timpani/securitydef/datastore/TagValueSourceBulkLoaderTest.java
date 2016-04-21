package org.fixtrading.timpani.securitydef.datastore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.fixtrading.timpani.securitydef.datastore.MongoDBSecurityDefinitionStore;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinition;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionRequestImpl;
import org.fixtrading.timpani.securitydef.messages.SecurityRequestType;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

//Uncomment to run this test. Prerequisite: datastore cluster must be running.
@Ignore
public class TagValueSourceBulkLoaderTest {

  private static final String FILENAME = "secdef.dat";
  private static final String SYMBOL = "BR:IR1J16Z16";
  private static final String CFI_CODE = "FFICSX";
  private static final String EXCHANGE_CODE = "XNYM";
  private static final String MARKET_SEGMENT_ID = "56";
  
  private MongoDBSecurityDefinitionStore store;


//  @BeforeClass
//  public static void setUpOnce() throws Exception {
//    String[] args = new String[] {FILENAME};
//    TagValueSourceBulkLoader.main(args);
//  }
  
  @Before
  public void setUp() throws Exception {   
    store = new MongoDBSecurityDefinitionStore();
    store.open().get();
  }

  @After
  public void tearDown() throws Exception {
    store.close();
  }
  
  class TestConsumer implements Consumer<SecurityDefinition> {

    private final Predicate<SecurityDefinition> predicate;

    public TestConsumer(Predicate<SecurityDefinition> predicate) {
      this.predicate = predicate;
    }

    @Override
    public void accept(SecurityDefinition t) {
      assertTrue(predicate.test(t));
    }
    
  }

  @Test
  public void selectSymbol() throws InterruptedException, ExecutionException {
    SecurityDefinitionRequestImpl securityDefinitionRequest = new SecurityDefinitionRequestImpl();
    securityDefinitionRequest.setSecurityRequestType(SecurityRequestType.Symbol);
    securityDefinitionRequest.setSymbol(SYMBOL);
    
    Predicate<SecurityDefinition> predicate = new Predicate<SecurityDefinition>() {

      @Override
      public boolean test(SecurityDefinition sd) {
        return SYMBOL.equals(sd.getSymbol());
      }
      
    };
    TestConsumer consumer =  new TestConsumer(predicate);
    int count = store.select(securityDefinitionRequest,
        consumer).get();
    assertEquals(1, count);
  }

  @Test
  public void selectcfiCode() throws InterruptedException, ExecutionException {
    SecurityDefinitionRequestImpl securityDefinitionRequest = new SecurityDefinitionRequestImpl();
    securityDefinitionRequest.setSecurityRequestType(SecurityRequestType.SecurityTypeAndOrCFICode);
    securityDefinitionRequest.setCfiCode(CFI_CODE);
    
    Predicate<SecurityDefinition> predicate = new Predicate<SecurityDefinition>() {

      @Override
      public boolean test(SecurityDefinition sd) {
        return CFI_CODE.equals(sd.getCfiCode());
      }
      
    };
    TestConsumer consumer =  new TestConsumer(predicate);
    int count = store.select(securityDefinitionRequest,
        consumer).get();
    assertTrue(count > 0);
  }
  
  @Test
  public void selectExchangeCode() throws InterruptedException, ExecutionException {
    SecurityDefinitionRequestImpl securityDefinitionRequest = new SecurityDefinitionRequestImpl();
    securityDefinitionRequest.setSecurityRequestType(SecurityRequestType.MarketIDOrMarketID);
    securityDefinitionRequest.setSecurityExchange(EXCHANGE_CODE);
    
    Predicate<SecurityDefinition> predicate = new Predicate<SecurityDefinition>() {

      @Override
      public boolean test(SecurityDefinition sd) {
        return EXCHANGE_CODE.equals(sd.getSecurityExchange());
      }
      
    };
    TestConsumer consumer =  new TestConsumer(predicate);
    int count = store.select(securityDefinitionRequest,
        consumer).get();
    assertTrue(count > 0);
  }
  
  @Test(expected = IllegalArgumentException.class) 
  public void selectBadRequest() throws InterruptedException, ExecutionException {
    SecurityDefinitionRequestImpl securityDefinitionRequest = new SecurityDefinitionRequestImpl();
    securityDefinitionRequest.setSecurityRequestType(SecurityRequestType.MarketIDOrMarketID);
    
    Predicate<SecurityDefinition> predicate = new Predicate<SecurityDefinition>() {

      @Override
      public boolean test(SecurityDefinition sd) {
        
        return MARKET_SEGMENT_ID.equals(sd.getMarketSegment(0).getMarketSegmentID());
      }
      
    };
    TestConsumer consumer =  new TestConsumer(predicate);
    int count = store.select(securityDefinitionRequest,
        consumer).get();
  }
  
  @Test
  public void selectMarketSegment() throws InterruptedException, ExecutionException {
    SecurityDefinitionRequestImpl securityDefinitionRequest = new SecurityDefinitionRequestImpl();
    securityDefinitionRequest.setSecurityRequestType(SecurityRequestType.MarketIDOrMarketID);
    securityDefinitionRequest.setMarketSegmentID(MARKET_SEGMENT_ID);
    
    Predicate<SecurityDefinition> predicate = new Predicate<SecurityDefinition>() {

      @Override
      public boolean test(SecurityDefinition sd) {
        
        return MARKET_SEGMENT_ID.equals(sd.getMarketSegment(0).getMarketSegmentID());
      }
      
    };
    TestConsumer consumer =  new TestConsumer(predicate);
    int count = store.select(securityDefinitionRequest,
        consumer).get();
    assertTrue(count > 0);
  }

}
