package com.seguridadsesion.webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jasypt.util.password.StrongPasswordEncryptor;

@Path("/seguridadsesion")
public class LoginAPIHelper {

	// USO:
	// http://localhost:8080/SeguridadSesionXML/seguridadsesion/{method}?{param1}={value1}&{param2}={value2}&...

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
		// Si existe, devuelve success=false
		// Si no existe, crea el usuario en la DB con activado=false -> LLAMADA
		// A WS DE INTEGRACION
		// Envia mail para confirmacion

		// Password encrypt
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(password);

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		// session.setReason("");
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

		// Verifica datos del usuario -> LLAMADA A WS DE INTEGRACION

		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encryptedPassword = "q6PWkDqQDAKObhh52owPKu2AcsvbThhkt2QEGIwvRY23XyRZ5y9WUqE9q6PT31pZ"; // La
																										// que
																										// obtiene
																										// de
																										// la
																										// BD

		Boolean success = false;
		if (passwordEncryptor.checkPassword(password, encryptedPassword)) {
			success = true;
		}

		SessionResponse session = new SessionResponse();

		// Si es correcta, genera token
		if (success) {
			String token = TokenManager.getInstance().getToken();
			TokenManager.getInstance().setToken(token, username);
			session.setAuthToken(token);
		}

		session.setSuccess(success);

		return session;
	}

	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse logout(@QueryParam("token") String authToken) {

		// Elimina la token
		Boolean success = TokenManager.getInstance().removeToken(authToken);

		SessionResponse session = new SessionResponse();
		session.setSuccess(success);
		return session;
	}

	@GET
	@Path("/istokenvalid")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse isTokenValid(@QueryParam("token") String authToken) {

		// Verifica que exista la token y de ser asi refresca el tiempo de
		// expiracion

		String username = TokenManager.getInstance().isTokenValid(authToken);

		SessionResponse session = new SessionResponse();

		Boolean success = false;

		if (username != "") {
			session.setUsername(username);
			success = true;
		} else {
			session.setReason("La token es inválida");
		}

		session.setSuccess(success);

		return session;
	}

	@GET
	@Path("/activateuser")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse activateUser(@QueryParam("username") String username) {

		// Se llama cuando el usuario confirma via email la cuenta
		// Setea como activado el usuario

		// Setea como activado al usuario -> LLAMADA A WS DE INTEGRACION

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

		String username = TokenManager.getInstance().isTokenValid(authToken);

		// Busca password del usuario -> LLAMADA A WS DE INTEGRACION
		String encryptedPassword = "";

		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

		Boolean success = true;
		// Chequea que sea la misma que oldPassword
		if (!passwordEncryptor.checkPassword(oldPassword, encryptedPassword)) {
			success = false;
		} else {
			// Encripta la nueva
			String newEncryptedPassword = passwordEncryptor
					.encryptPassword(newPassword);
			// Actualiza password en BD -> LLAMADA A WS DE INTEGRACION

		}

		//

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

		String username = TokenManager.getInstance().isTokenValid(authToken);

		// Genera password aleatorio (uso un getToken)
		String newPassword = TokenManager.getInstance().getToken();

		// Lo guarda como password del usuario -> LLAMADA A WS DE INTEGRACION

		// Envia mail al user con nueva password

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

		String username = TokenManager.getInstance().isTokenValid(authToken);

		// Setea en BD habilitada=false -> LLAMADA A WS DE INTEGRACION

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

	@GET
	@Path("/enableaccount")
	@Produces(MediaType.APPLICATION_XML)
	public SessionResponse enableAccount(@QueryParam("token") String authToken,
			@QueryParam("userid") String userId) {

		String username = TokenManager.getInstance().isTokenValid(authToken);

		// Setea en BD habilitada=true -> LLAMADA A WS DE INTEGRACION

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return session;
	}

}
