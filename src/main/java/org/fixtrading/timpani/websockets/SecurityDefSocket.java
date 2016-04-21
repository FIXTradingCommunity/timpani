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
package org.fixtrading.timpani.websockets;

import java.io.IOException;
import java.util.function.Consumer;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.api.CloseStatus;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.fixtrading.timpani.codec.JsonCodec;
import org.fixtrading.timpani.securitydef.SecurityDefinitionService;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinition;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionImpl;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionRequest;
import org.fixtrading.timpani.securitydef.messages.SecurityDefinitionRequestImpl;

/**
 * Websocket implementation for text messages
 * 
 * @author Don Mendelson
 *
 */
@WebSocket(maxTextMessageSize = 64 * 1024)
public class SecurityDefSocket implements Consumer<SecurityDefinitionImpl> {

  private final JsonCodec codec = new JsonCodec();
  private final Logger logger;
  private final SecurityDefinitionService service;
  private Session session;

  public SecurityDefSocket(SecurityDefinitionService service) {
    this.service = service;
    logger = Log.getRootLogger();
  }

  @Override
  public void accept(SecurityDefinitionImpl securityDefinition) {
    try {
      String messageToSend = encodeResponse(securityDefinition);
      session.getRemote().sendString(messageToSend);
    } catch (IOException ex) {
      logger.warn("Send failed", ex);
      session.close(new CloseStatus(1, ex.getMessage()));
    }
  }

  /**
   * Handle a received message
   * 
   * @param session websocket session on which the message was received
   * @param message a text message
   */
  @OnWebSocketMessage
  public void onText(Session session, String message) {
    this.session = session;
    SecurityDefinitionRequest securityDefinitionRequest = decodeRequest(message);

    logger.debug("Request received on session %s: %s", session, message);
    service.serve(securityDefinitionRequest, this).whenComplete((count, error) -> {
      if (error != null) {
        logger.warn(error);
        session.close(new CloseStatus(1, error.getMessage()));
      }

    });
  }
  
  SecurityDefinitionRequest decodeRequest(String message) {
    return codec.decode(message, SecurityDefinitionRequestImpl.class);
  }
  
  String encodeResponse(SecurityDefinition securityDefinition) {
    return codec.encode(securityDefinition);
  }
}
