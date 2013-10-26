package com.seguridadsesion.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ApiServiceHelper {
	
	@WebMethod public String login(String name); 

}
