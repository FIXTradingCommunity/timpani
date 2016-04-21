package org.fixtrading.timpani.securitydef;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.fixtrading.timpani.securitydef.datastore.MongoDBSecurityDefinitionStore;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionImpl;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionRequest;
import org.fixtrading.timpani.securitydef.messages.SecurityRequestResult;

public class SecurityDefinitionServiceImpl implements SecurityDefinitionService {

  private final MongoDBSecurityDefinitionStore store = new MongoDBSecurityDefinitionStore();
  private final Logger logger;


  public SecurityDefinitionServiceImpl() throws InterruptedException, ExecutionException {
    logger = Log.getRootLogger();
    store.open().get();
  }

  @Override
  public void close() throws Exception {
    store.close();
    logger.info("SecurityDefinitionServiceImpl closed");
  }

  @Override
  public CompletableFuture<Integer> serve(
      SecurityDefinitionRequest securityDefinitionRequest, Consumer<SecurityDefinitionImpl> consumer) {
    logger.debug("SecurityDefinitionServiceImpl serving request %s", securityDefinitionRequest);

    return store.select(securityDefinitionRequest, sd -> {
      SecurityDefinitionImpl impl = (SecurityDefinitionImpl) sd;
      impl.setSecurityReqID(securityDefinitionRequest.getSecurityReqID());
      impl.setSubscriptionRequestType(securityDefinitionRequest.getSubscriptionRequestType());
      impl.setSecurityRequestResult(SecurityRequestResult.ValidRequest);
      consumer.accept(impl);
    }).handle((count, error) -> {
      if (error == null) {
        if (count == 0) {
          logger.debug("SecurityDefinitionServiceImpl: Zero security defintions returned" );
          SecurityDefinitionImpl securityDefinition = new SecurityDefinitionImpl();
          securityDefinition.setSecurityReqID(securityDefinitionRequest.getSecurityReqID());
          securityDefinition.setSubscriptionRequestType(securityDefinitionRequest.getSubscriptionRequestType());
          securityDefinition.setSecurityRequestResult(SecurityRequestResult.NoInstrumentsFound);
          consumer.accept(securityDefinition);
        }
      } else {
        logger.warn("SecurityDefinitionServiceImpl error %s", error.getMessage());
        SecurityDefinitionImpl securityDefinition = new SecurityDefinitionImpl();
        securityDefinition.setSecurityReqID(securityDefinitionRequest.getSecurityReqID());
        securityDefinition.setSubscriptionRequestType(securityDefinitionRequest.getSubscriptionRequestType());
        securityDefinition.setSecurityRequestResult(SecurityRequestResult.InstrumentDataTemporarilyUnavailable);
        consumer.accept(securityDefinition);
      }
      return count;
    });
  }
}
