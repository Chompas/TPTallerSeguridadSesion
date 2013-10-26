package com.seguridadsesion.webservice;

import javax.jws.WebService;

@WebService(endpointInterface="com.seguridadsesion.webservice.ApiServiceHelper")
public class ApiServiceHelperImpl implements ApiServiceHelper {

	public String login(String name) {  
		return "Hello world from "+name;  
	}  
}
