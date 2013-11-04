package com.seguridadsesion.webservice;

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

		// Llama a integracion y verifica la existencia del usuario.
		// De no ser asi, crea el usuario en la DB y envia mail para
		// confirmacion

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		//session.setReason("");
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

		// Verifica datos del usuario y genera token

		SessionResponse session = new SessionResponse();
		String token = TokenManager.getInstance().getToken();
		TokenManager.getInstance().setToken(token);
		session.setAuthToken(token);
		session.setSuccess(true);

		return session;
	}

	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse logout(@QueryParam("token") String authToken) {
		
		Boolean success = TokenManager.getInstance().removeToken(authToken);

		SessionResponse session = new SessionResponse();
		session.setSuccess(success);
		return session;
	}

	@GET
	@Path("/istokenvalid")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse isTokenValid(@QueryParam("token") String authToken) {

		// Verifica que exista la token

		Boolean result = TokenManager.getInstance().isTokenValid(authToken);

		SessionResponse session = new SessionResponse();

		if (result) {
			// TODO: Va a buscar el nombre del usuario
			session.setUsername("user1");
		} else {
			session.setReason("La token es inválida");
		}

		session.setSuccess(result);

		return session;
	}

	@GET
	@Path("/activateuser")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse activateUser(@QueryParam("username") String username) {

		// Se llama cuando el usuario confirma via email la cuenta
		// Setea como activado el usuario

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

		// Verifica oldPassword y de ser correcta, actualiza datos en BD

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/resetpassword")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse resetPassword(@QueryParam("token") String authToken,
			@QueryParam("userid") String userId) {

		// Resetea la password

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

		// Setea la cuenta como desactivada

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/enableaccount")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse enableAccount(@QueryParam("token") String authToken,
			@QueryParam("userid") String userId) {

		// Activa la cuenta desactivada

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

}
