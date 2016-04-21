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

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.fixtrading.timpani.securitydef.SecurityDefinitionService;

/**
 * 
 * @author Don Mendelson
 * 
 * @see <a href="https://www.iana.org/assignments/websocket/websocket.xml"/>
 * todo: handle specific subprotocol
 */
public class SecurityDefinitionCreator implements WebSocketCreator {

  private final SecurityDefinitionService service;
  private final Logger logger;

  public SecurityDefinitionCreator(SecurityDefinitionService service) {
		this.service = service;
		logger = Log.getRootLogger();
	}

	/**
	 * Create a websocket from the incoming request.
	 * 
	 * @param req
	 *            the request details
	 * @param resp
	 *            the response details
	 * @return a websocket object to use, or null if no websocket should be
	 *         created from this request.
	 */
	@Override
	public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
	  logger.info("Creating new SecurityDefSocket");
	  return new SecurityDefSocket(service);
	}


}
