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

/**
 * Functions for creating a websocket and handling its events
 */
var ws = (function() {

	var websocket;

	function wsUriFromHttp(wsPath) {
		var httpUri = window.location.href;
		// http => ws, https => wss
		var wsUri = httpUri.replace("http", "ws");
		// URI already ends in / so don't add another
		return wsUri.concat(wsPath);
	}
	;

	function initWebSocket(wsUri, messageEventHandler, errorEventHandler,
			openEventHandler, closeEventHandler, protocol) {
		websocket = new WebSocket(wsUri, protocol);
		websocket.addEventListener("message", messageEventHandler);
		websocket.addEventListener("error", errorEventHandler);
		websocket.addEventListener("open", openEventHandler);
		websocket.addEventListener("close", closeEventHandler);
	}
	;

	return {
		/**
		 * Opens a websocket to HTTP server URI is ws://[server]/[path]
		 */
		init : function(path, messageEventHandler, errorEventHandler,
				openEventHandler, closeEventHandler, protocol) {
			if ("WebSocket" in window) {
				var wsPath = path || "/";
				var wsUri = wsUriFromHttp(wsPath);
				initWebSocket(wsUri, messageEventHandler, errorEventHandler,
						openEventHandler, closeEventHandler, protocol);
			} else {
				alert("WebSocket NOT supported by your Browser!");
			}
		},

		/**
		 * Message may be String, Blob, ArrayBuffer, or ArrayBufferView
		 * depending on text or binary subprotocol
		 */
		send : function(message) {
			websocket.send(message);
		},

		/**
		 * Close the connection
		 */
		disconnect : function() {
			websocket.close();
		}
	}
})();