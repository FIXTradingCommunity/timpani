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

import java.util.concurrent.ExecutionException;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.fixtrading.timpani.securitydef.SecurityDefinitionService;
import org.fixtrading.timpani.securitydef.SecurityDefinitionServiceImpl;

/**
 * Servlet implementation
 * <p>
 * Jetty provides the ability to wire up WebSocket endpoints to Servlet Path Specs via the use of a
 * WebSocketServlet bridge servlet. Internally, Jetty manages the HTTP Upgrade to WebSocket and
 * migration from a HTTP Connection to a WebSocket Connection.
 * 
 * @author Don Mendelson
 */
// @WebServlet(urlPatterns = "/SecurityDefinitions")
public class SecurityDefinitionServlet extends WebSocketServlet {
  private static final long serialVersionUID = 1L;

  private SecurityDefinitionService service;
  private Logger logger;

  @Override
  public void configure(WebSocketServletFactory factory) {
    logger = Log.getRootLogger();
    factory.getPolicy().setIdleTimeout(120000);
    // factory.register(SecurityDefSocket.class);
    try {
      service = new SecurityDefinitionServiceImpl();
      factory.setCreator(new SecurityDefinitionCreator(service));
      logger.info("SecurityDefinitionServlet configured");

    } catch (InterruptedException | ExecutionException e) {
      logger.warn("SecurityDefinitionServlet configuration failed", e);
      throw new RuntimeException(e);
    }
  }

//  @Override
//  public void destroy() {
//    try {
//      service.close();
//    } catch (Exception e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    super.destroy();
//  }
//
//  @Override
//  public void init() throws ServletException {
//    super.init();
//    try {
//      service = new SecurityDefinitionServiceImpl();
//    } catch (InterruptedException | ExecutionException e) {
//      throw new ServletException(e);
//    }
//  }

}
