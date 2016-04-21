package org.fixtrading.timpani.securitydef.datastore;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import org.fixtrading.timpani.securitydef.datastore.MongoDBSecurityDefinitionStore;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinition;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionImpl;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionRequestImpl;
import org.fixtrading.timpani.securitydef.messages.SecurityIDSource;
import org.fixtrading.timpani.securitydef.messages.SecurityRequestType;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionImpl.MarketSegmentImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

// Uncomment to run this test. Prerequisite: datastore cluster must be running.
@Ignore
public class MongoDBSecurityDefinitionStoreTest {

  private MongoDBSecurityDefinitionStore store;
  class TestConsumer implements Consumer<SecurityDefinition> {
    public int getSelected() {
      return selected;
    }
    public void setSelected(int selected) {
      this.selected = selected;
    }
    private int selected = 0;
    @Override
    public void accept(SecurityDefinition t) {
      selected++;
    }
    
  };

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    // todo: invoke script to start server
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    // todo: invoke script to stop server
  }

  @Before
  public void setUp() throws Exception {
    store = new MongoDBSecurityDefinitionStore();
    store.open().get();
  }

  @After
  public void tearDown() throws Exception {
    store.close();
  }

  @Test
  public void insertSelectAndDeleteOne() throws InterruptedException, ExecutionException, TimeoutException {
    SecurityDefinitionImpl securityDefinition = new SecurityDefinitionImpl();
    String symbol = "GE";
    securityDefinition.setSymbol(symbol);
    String marketID = "US";
    MarketSegmentImpl marketSegment = securityDefinition.addMarketSegment();
    marketSegment.setMarketID(marketID);
    String securityID = "BBG000BK6MB5";
    securityDefinition.setSecurityID(securityID);
    securityDefinition.setSecurityIDSource(SecurityIDSource.FinancialInstrumentGlobalIdentifier);
    String securityType = "CS";
    securityDefinition.setSecurityType(securityType);
    String securityDesc = "GENERAL ELECTRIC CO";
    securityDefinition.setSecurityDesc(securityDesc);

    store.insert(securityDefinition).get(5, SECONDS);

    SecurityDefinitionRequestImpl request = new SecurityDefinitionRequestImpl();
    request.setSecurityRequestType(SecurityRequestType.Symbol);
    request.setSymbol(symbol);
    TestConsumer consumer = new TestConsumer();
    store.select(request, consumer).get(5, SECONDS);
    assertEquals(1, consumer.selected);

    store.delete(request).get(5, SECONDS);

    consumer.selected = 0;
    store.select(request, consumer).get(5, SECONDS);
    assertEquals(0, consumer.selected);
  }

}
