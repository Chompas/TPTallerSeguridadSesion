package com.seguridadsesion.webservice;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/seguridadsesion")
public class LoginAPIHelper {

	// USO:
	// http://localhost:8080/SeguridadSesionXML/seguridadsesion/registeruser?username=javi29c&password=123456&nombre=Javier&apellido=Lara&padron=93343&fechaNac=11/10/1991&email=javi29c@yahoo.com&rol=1

	/*
	 * registeruser
	 * 
	 * Recibe un diccionario con los datos de registro (apellido, nombres,
	 * padrón, fecha de nacimiento, email y password) y el rol que va a tener el
	 * usuario. Valida los datos, encripta el password y registra al usuario
	 * (interactuando con la capa de Persistencia). Lo deja pendiente hasta la
	 * confirmación por mail. Devuelve XML con resultado de la operación y la
	 * razón del error en caso de existir.
	 */

	@GET
	@Path("/registeruser")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse registerUser(
			@QueryParam("username") String username,
			@QueryParam("password") String password,
			@QueryParam("nombres") String nombres,
			@QueryParam("apellido") String apellido,
			@QueryParam("padron") String padron,
			@QueryParam("fecha") String fecha,
			@QueryParam("email") String email, @QueryParam("rol") int rol) {

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		session.setReason(email);
		return session;
	}

	/*
	 * 
	 */

	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse login(@QueryParam("username") String username,
			@QueryParam("password") String password) {
		SessionResponse session = new SessionResponse();
		session.setAuthToken(new BigInteger(130, new SecureRandom()).toString(32));
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse logout(@QueryParam("token") String authToken) {
		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/istokenvalid")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse isTokenValid(@QueryParam("token") String authToken) {
		SessionResponse session = new SessionResponse();
		session.setUsername("user1");
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/activateuser")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse activateUser(@QueryParam("username") String username) {
		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/changepassword")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse changePassword(
			@QueryParam("token") String authToken,
			@QueryParam("oldpassword") String oldPassword,
			@QueryParam("newpassword") String newPassword,
			@QueryParam("userid") String userId) {
		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/resetpassword")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse resetPassword(@QueryParam("token") String authToken,
			@QueryParam("userid") String userId) {
		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/disableaccount")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse disableAccount(
			@QueryParam("token") String authToken,
			@QueryParam("userid") String userId) {
		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/enableaccount")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse enableAccount(@QueryParam("token") String authToken,
			@QueryParam("userid") String userId) {
		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

}
