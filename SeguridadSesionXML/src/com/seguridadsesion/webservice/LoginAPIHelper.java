package com.seguridadsesion.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/seguridadsesion")
public class LoginAPIHelper {

	  @GET
	  @Path("/registeruser")
	  @Produces (MediaType.APPLICATION_XML)
	  public SessionResponse registerUser() {
		  SessionResponse session = new SessionResponse();
		  session.setAuthToken("fdsjf5s61f6sad21f3as");
		  session.setSuccess(true);
		  return session;
	  }
	  
}
