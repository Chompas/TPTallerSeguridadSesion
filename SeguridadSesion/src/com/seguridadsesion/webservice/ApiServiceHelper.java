package com.seguridadsesion.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.seguridadsesion.objects.SessionResponse;

@WebService
public interface ApiServiceHelper {
	
	@WebMethod public SessionResponse login(
			@WebParam(name = "user")String user
			); 

}
