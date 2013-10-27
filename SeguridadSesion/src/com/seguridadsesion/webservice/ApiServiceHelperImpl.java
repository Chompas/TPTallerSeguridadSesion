package com.seguridadsesion.webservice;

import javax.jws.WebService;
import com.seguridadsesion.objects.SessionResponse;

@WebService(endpointInterface="com.seguridadsesion.webservice.ApiServiceHelper")
public class ApiServiceHelperImpl implements ApiServiceHelper {

	public SessionResponse login(String user) {
		
		//Here is useful to know that if we don't initialize a parameter (for example reason)
		//the xml will not return that tag which is perfect
		
		SessionResponse sessionResponse = new SessionResponse();
		sessionResponse.authToken = "ofksdfj98123j9asd092";
		sessionResponse.success = true;
		
		return sessionResponse;
	}  
}
