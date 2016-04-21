/**
 * Copyright 2016 FIX Protocol Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package org.fixtrading.timpani.securitydef.datastore;

import static com.mongodb.client.model.Filters.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.fixtrading.timpani.codec.JsonCodec;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinition;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionImpl;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionRequest;
import org.fixtrading.timpani.securitydef.messages.SecurityRequestType;

import com.mongodb.Block;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.connection.ClusterSettings;

public class MongoDBSecurityDefinitionStore implements SecurityDefinitionStore {

  class SecurityDefinitionBlock implements Block<Document> {

    private final Consumer<SecurityDefinitionImpl> consumer;
    private int count = 0;

    public SecurityDefinitionBlock(Consumer<SecurityDefinitionImpl> consumer) {
      this.consumer = consumer;
    }

    @Override
    public void apply(Document d) {
      count++;
      SecurityDefinitionImpl securityDefinition =
          jsonCodec.decode(d.toJson(), SecurityDefinitionImpl.class);
      consumer.accept(securityDefinition);
    }

    public int getCount() {
      return count;
    }
  };

  private static final String DATABASE_NAME = "refdata";
  private static final String SECDEF_COLLECTION_NAME = "secdef";
  private MongoCollection<Document> collection;
  private MongoDatabase database;
  private String[] hosts = {"localhost"};
  private final JsonCodec jsonCodec = new JsonCodec();
  private MongoClient mongoClient;

  /**
   * Constructor with default localhost
   */
  public MongoDBSecurityDefinitionStore() {}

  /**
   * Constructor with specified hosts
   * 
   * @param hosts array of host addresses of MongoDB cluster
   */
  public MongoDBSecurityDefinitionStore(String[] hosts) {
    this.hosts = hosts;
  }

  @Override
  public void close() {
    mongoClient.close();
  }

  public CompletableFuture<SecurityDefinitionStore> delete(
      SecurityDefinitionRequest securityDefinitionRequest) {

    CompletableFuture<SecurityDefinitionStore> future =
        new CompletableFuture<SecurityDefinitionStore>();

    // todo: handle different request types
    collection.deleteMany(eq("symbol", securityDefinitionRequest.getSymbol()),
        (DeleteResult result, final Throwable t) -> {
          if (t == null) {
            future.complete(this);
          } else {
            future.completeExceptionally(t);
          }
        });
    return future;
  }

  @Override
  public CompletableFuture<SecurityDefinitionStore> insert(
      Collection<SecurityDefinition> securityDefinitions) {
    CompletableFuture<SecurityDefinitionStore> future =
        new CompletableFuture<SecurityDefinitionStore>();

    List<Document> docs = securityDefinitions.stream().map(s -> {
      final String json = jsonCodec.encode(s);
      return Document.parse(json);
    }).collect(Collectors.toList());

    collection.insertMany(docs, (Void result, final Throwable t) -> {
      if (t == null) {
        future.complete(this);
      } else {
        future.completeExceptionally(t);
      }
    });
    return future;
  }

  @Override
  public CompletableFuture<SecurityDefinitionStore> insert(SecurityDefinition securityDefinition) {
    CompletableFuture<SecurityDefinitionStore> future =
        new CompletableFuture<SecurityDefinitionStore>();

    final String json = jsonCodec.encode(securityDefinition);
    // convert json to bson
    Document doc = Document.parse(json);
    collection.insertOne(doc, (Void result, final Throwable t) -> {
      if (t == null) {
        future.complete(this);
      } else {
        future.completeExceptionally(t);
      }
    });
    return future;
  }

  @Override
  public CompletableFuture<SecurityDefinitionStore> open() {
    List<ServerAddress> hostList =
        Arrays.stream(hosts).map(h -> new ServerAddress(h)).collect(Collectors.toList());
    ClusterSettings clusterSettings = ClusterSettings.builder().hosts(hostList).build();
    MongoClientSettings settings =
        MongoClientSettings.builder().clusterSettings(clusterSettings).build();
    mongoClient = MongoClients.create(settings);

    database = mongoClient.getDatabase(DATABASE_NAME);
    collection = database.getCollection(SECDEF_COLLECTION_NAME);

    // In the case of MongoDB, open is synchronous because it doesn't
    // actually communicate with the server until a query is invoked.
    return CompletableFuture.completedFuture(this);
  }

  @Override
  public CompletableFuture<Integer> select(SecurityDefinitionRequest securityDefinitionRequest,
      Consumer<? extends SecurityDefinition> consumer) {

    CompletableFuture<Integer> future = new CompletableFuture<Integer>();

    Bson filter = null;
    SecurityRequestType securityRequestType = securityDefinitionRequest.getSecurityRequestType();
    switch (securityRequestType) {
      case RequestSecurityIdentityAndSpecifications:
        break;
      case RequestListSecurities:
        break;
      case Symbol:
        filter = eq("symbol", securityDefinitionRequest.getSymbol());
        break;
      case SecurityTypeAndOrCFICode: {
        String securityType = securityDefinitionRequest.getSecurityType();
        String cfiCode = securityDefinitionRequest.getCfiCode();
        if (securityType != null && cfiCode != null) {
          filter = Filters.or(eq("securityType", securityDefinitionRequest.getSecurityType()),
              eq("cfiCode", securityDefinitionRequest.getCfiCode()));
        } else if (securityType != null) {
          filter = eq("securityType", securityDefinitionRequest.getSecurityType());
        } else if (cfiCode != null) {
          filter = eq("cfiCode", securityDefinitionRequest.getCfiCode());
        } else {
          throw new IllegalArgumentException("Missing security type or CFI code");
        }
      }
        break;
      case Product: {
        String product = securityDefinitionRequest.getProduct();
        String securityGroup = securityDefinitionRequest.getSecurityGroup();
        if (product != null) {
          filter = eq("product", product);
        } else if (securityGroup != null) {
          filter = eq("securityGroup", securityGroup);
        } else {
          throw new IllegalArgumentException("Missing product");
        }
      }
        break;
      case TradingSessionID:
        filter = eq("tradingSessionID", securityDefinitionRequest.getTradingSessionID());
        break;
      case AllSecurities:
        filter = exists("symbol");
        break;
      case MarketIDOrMarketID: {
        String marketId = securityDefinitionRequest.getMarketID();
        String marketSegmentID = securityDefinitionRequest.getMarketSegmentID();
        String securityExchange = securityDefinitionRequest.getSecurityExchange();
        if (marketId != null) {
          filter = elemMatch("marketSegmentGrp", Filters.eq("marketID", marketId));
        } else if (marketSegmentID != null) {
          filter = elemMatch("marketSegmentGrp", Filters.eq("marketSegmentID", marketSegmentID));
        } else if (securityExchange != null) {
          filter = eq("securityExchange", securityExchange);
        } else {
          throw new IllegalArgumentException("Missing market or market segment");
        }
      }
        break;
      default:
        throw new UnsupportedOperationException("Request type not supported");
    }

    @SuppressWarnings("unchecked")
    SecurityDefinitionBlock block =
        new SecurityDefinitionBlock((Consumer<SecurityDefinitionImpl>) consumer);

    SingleResultCallback<Void> callback = new SingleResultCallback<Void>() {

      @Override
      public void onResult(Void result, Throwable t) {
        if (t == null) {
          future.complete(block.getCount());
        } else {
          future.completeExceptionally(t);
        }
      }
    };


    collection.find(filter).forEach(block, callback);

    return future;
  }

  @Override
  public CompletableFuture<SecurityDefinitionStore> truncate() {
    CompletableFuture<SecurityDefinitionStore> future =
        new CompletableFuture<SecurityDefinitionStore>();

    collection.drop((Void result, final Throwable t) -> {
      if (t == null) {
        collection = database.getCollection(SECDEF_COLLECTION_NAME);

        future.complete(this);
      } else {
        future.completeExceptionally(t);
      }
    });
    return future;
  }


}
