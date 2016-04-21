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

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.fixtrading.timpani.securitydef.messages.SecurityDefinition;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionRequest;

/**
 * Interface for security definition data store
 * 
 * @author Don Mendelson
 *
 */
public interface SecurityDefinitionStore extends AutoCloseable {

  /**
   * Delete a security definition that matches the characteristics of the request
   * 
   * @param securityDefinitionRequest a request
   * @return future provides asynchronous result
   */
  CompletableFuture<SecurityDefinitionStore> delete(
      SecurityDefinitionRequest securityDefinitionRequest);

  /**
   * Insert a collection of security definitions into the store
   * 
   * @param securityDefinitions a collection of populated objects
   * @return future provides asynchronous result
   */
  CompletableFuture<SecurityDefinitionStore> insert(
      Collection<SecurityDefinition> securityDefinitions);

  /**
   * Insert a single security definition into the store
   * 
   * @param securityDefinition a populated object
   * @return future provides asynchronous result
   */
  CompletableFuture<SecurityDefinitionStore> insert(SecurityDefinition securityDefinition);

  /**
   * Open communications with the data store. It must be opened successfully before any other
   * operations may be executed.
   * 
   * @return future provides asynchronous result
   */
  CompletableFuture<SecurityDefinitionStore> open();

  /**
   * Retrieve security definitions from the store that fulfill the request
   * 
   * @param securityDefinitionRequest a populated request
   * @param consumer of selected security definitions
   * @return future signals when operation is complete and returns the total
   * number of security definitions returned
   */
  CompletableFuture<Integer> select(
      SecurityDefinitionRequest securityDefinitionRequest,
      Consumer<? extends SecurityDefinition> consumer);

  /**
   * Truncate <em>all</em> security definitions. Use with care.
   * 
   * @return future provides asynchronous result
   */
  CompletableFuture<SecurityDefinitionStore> truncate();
}
