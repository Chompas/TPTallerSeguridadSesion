package wtp;

import java.rmi.RemoteException;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class LoginAPIHelper {

	private XmlUtil xmlutil = new XmlUtil();

	public String registerUser(String username, String password,
			String nombres, String apellido, String padron, String fecha,
			String email, int rol) {

		SessionResponse session = new SessionResponse();

		User user = new User();

		user.setUsername(username);

		// Llama a integracion y verifica la existencia del usuario.
		IntegracionStub integracion = null;
		IntegracionStub.SeleccionarDatos selectRequest = null;
		IntegracionStub.SeleccionarDatos selectResponse = null;

		try {
			integracion = new IntegracionStub();
			selectRequest = new IntegracionStub.SeleccionarDatos();
			selectRequest.setXml("<?xml version=\"1.0\"?><WS>"
					+ xmlutil.convertToXml(user, User.class) + "</WS>");
			selectResponse = integracion.seleccionarDatos(selectRequest);
		} catch (RemoteException e) {
			System.err.println(e.toString());
		}

		if (user.usuarioExistente(selectResponse.get_return())) {
			session.setSuccess(false);
			session.setReason("Usuario existente");
		} else {
			// Password encrypt
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			String encryptedPassword = passwordEncryptor.encryptPassword(password);
			
			IntegracionStub.GuardarDatos saveRequest = null;
			IntegracionStub.GuardarDatosResponse saveResponse = null;
			
			try {
				
				user.setActivado(false);
				user.setApellido(apellido);
				user.setEmail(email);
				user.setFechaNac(fecha);
				user.setHabilitado(true);
				user.setNombre(nombres);
				user.setPadron(padron);
				user.setPassword(encryptedPassword);
				
				integracion = new IntegracionStub();
				saveRequest = new IntegracionStub.GuardarDatos();
				
				saveRequest.setXml("<?xml version=\"1.0\"?>" + "<WS>" + xmlutil.convertToXml(user, User.class) + "</WS>");
				
				saveResponse = integracion.guardarDatos(saveRequest);
				
			} catch (RemoteException excepcionDeInvocacion) {
				System.err.println(excepcionDeInvocacion.toString());
				
			}
			
			// Envia mail para confirmacion
			
			session.setSuccess(true);
		}

		return xmlutil.convertToXml(session, SessionResponse.class);
	}


	public String login(String username, String password) {

		// Verifica datos del usuario -> LLAMADA A WS DE INTEGRACION

		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		// HACK para pass=123456
		String encryptedPassword = "q6PWkDqQDAKObhh52owPKu2AcsvbThhkt2QEGIwvRY23XyRZ5y9WUqE9q6PT31pZ";
		// La que obtiene de la BD

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

		return xmlutil.convertToXml(session, SessionResponse.class);
	}

	public String logout(String authToken) {

		// Elimina la token
		Boolean success = TokenManager.getInstance().removeToken(authToken);

		SessionResponse session = new SessionResponse();
		session.setSuccess(success);
		return xmlutil.convertToXml(session, SessionResponse.class);
	}

	public String isTokenValid(String authToken) {

		// Verifica que exista la token y de ser asi refresca el tiempo de
		// expiracion

		String username = TokenManager.getInstance().isTokenValid(authToken);

		SessionResponse session = new SessionResponse();

		Boolean success = false;

		if (username != "") {
			session.setUsername(username);
			success = true;
		} else {
			session.setReason("La token es inv�lida");
		}

		session.setSuccess(success);

		return xmlutil.convertToXml(session, SessionResponse.class);
	}

	public String activateUser(String username) {

		// Se llama cuando el usuario confirma via email la cuenta
		// Setea como activado el usuario

		// Setea como activado al usuario -> LLAMADA A WS DE INTEGRACION

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return xmlutil.convertToXml(session, SessionResponse.class);
	}

	public String changePassword(String authToken, String oldPassword,
			String newPassword, String userId) {

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
		return xmlutil.convertToXml(session, SessionResponse.class);
	}

	public String resetPassword(String authToken, String userId) {

		// Resetea la password

		String username = TokenManager.getInstance().isTokenValid(authToken);

		// Genera password aleatorio (uso un getToken)
		String newPassword = TokenManager.getInstance().getToken();

		// Lo guarda como password del usuario -> LLAMADA A WS DE INTEGRACION

		// Envia mail al user con nueva password

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return xmlutil.convertToXml(session, SessionResponse.class);
	}

	public String disableAccount(String authToken, String userId) {

		String username = TokenManager.getInstance().isTokenValid(authToken);

		// Setea en BD habilitada=false -> LLAMADA A WS DE INTEGRACION

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return xmlutil.convertToXml(session, SessionResponse.class);
	}

	public String enableAccount(String authToken, String userId) {

		String username = TokenManager.getInstance().isTokenValid(authToken);

		// Setea en BD habilitada=true -> LLAMADA A WS DE INTEGRACION

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return xmlutil.convertToXml(session, SessionResponse.class);
	}

}