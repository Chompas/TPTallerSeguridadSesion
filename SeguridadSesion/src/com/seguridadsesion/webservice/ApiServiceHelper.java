package com.seguridadsesion.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.seguridadsesion.objects.SessionResponse;

@WebService
public interface ApiServiceHelper {
	
	@WebMethod public SessionResponse login(
			@WebParam(name = "username") String username,
			@WebParam(name = "password") String password
			); 
	
	@WebMethod public SessionResponse registerUser(
			@WebParam(name = "username") String username,
			@WebParam(name = "password") String password,
			@WebParam(name = "nombre") String nombre,
			@WebParam(name = "apellido") String apellido,
			@WebParam(name = "padron") String padron,
			@WebParam(name = "fechaNac") String fechaNac,
			@WebParam(name = "email") String email,
			@WebParam(name = "rol") int rol
			);

}
