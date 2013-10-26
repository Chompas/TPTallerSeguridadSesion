package com.seguridadsesion.webservice;

import javax.jws.WebService;
import com.seguridadsesion.objects.SessionResponse;

@WebService(endpointInterface="com.seguridadsesion.webservice.ApiServiceHelper")
public class ApiServiceHelperImpl implements ApiServiceHelper {

	public SessionResponse login(String user) {
		
		SessionResponse sessionResponse = new SessionResponse();
		sessionResponse.authToken = "ofksdfj98123j9asd092";
		sessionResponse.reason = "";
		sessionResponse.success = true;
		
		return sessionResponse;
	}  
}
