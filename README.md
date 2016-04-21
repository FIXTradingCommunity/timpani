# Timpani

Timpani is a proof of concept for the use of FIX semantics with web technologies.

## Status
This project is a **prototype**. Participation in the project is encouraged by FIX Trading Community members as well as from any interested GitHub users.

## Protocol stack

### Application layer
The demonstrated FIX semantics is in the realm of reference data about securities available to trade. A web client sends a query in the form of a FIX Security Definition Request message. The server responds with one or more Security Definition messages.

### Presentation layer
Request and response messages are encoded in JavaScript Object Notation (JSON). Separately, a user guide is being prepared for FIX to JSON mapping.

#### Transport layer
Messages between web client and server are conveyed by a WebSocket transport. The protocol enhances HTTP to leave the TCP connection open after the initial response is delivered. Then messages can be fed to the client asynchronously. This is useful for security definitions since result sets can be huge -- better to pipeline delivery of results rather than collecting it for one synchronous dump. Furthermore, some markets create new instruments intra-day. Asynchronous transport supports unsolicited push to the client.

WebSocket protocol is supported by all popular browers. In this project, the server 
implementation is provided by Jetty.

### Datastore
For this implementation, security definitions are stored in MongoDB, a popular, open-source package. It stores records as BSON documents. BSON is an performance-enhanced JSON equivalent.

## License
© Copyright 2016 FIX Protocol Limited

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Prerequisites
This project requires Java 8. It should run on any platform for which the JVM is supported.

## Build
The project is built with Maven. 

