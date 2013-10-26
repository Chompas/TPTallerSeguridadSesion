package com.seguridadsesion.webservice;

import javax.xml.ws.Endpoint;

public class ApiServiceHelperWSPublisher {

	public static void main(String[] args) {
		 Endpoint.publish("http://localhost:8080/WS/ApiServiceHelper",new ApiServiceHelperImpl());

	}

}
