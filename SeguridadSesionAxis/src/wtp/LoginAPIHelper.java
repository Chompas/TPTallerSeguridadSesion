package wtp;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.ws.services.IntegracionStub;

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
		IntegracionStub.SeleccionarDatosResponse selectResponse = null;

		try {
			integracion = new IntegracionStub();
			selectRequest = new IntegracionStub.SeleccionarDatos();
			String xml = xmlutil.convertToXml(new UserWSForSelect(user), UserWSForSelect.class);
			selectRequest.setXml(xml);
			selectResponse = integracion.seleccionarDatos(selectRequest);
			System.out.println("INTEGRACION RESPONSE: "+selectResponse.get_return());
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
				
				saveRequest.setXml(xmlutil.convertToXml(new UserWSForSelect(user), UserWSForSelect.class));
				
				saveResponse = integracion.guardarDatos(saveRequest);
				
				// Falta chequear que se hizo bien el save, en ese caso, env’amos mail
				// Falta enviar al usuario que se registr—
				
				String activationToken = TokenManager.getInstance().getToken();
				TokenManager.getInstance().setActivationToken(activationToken, username);
				
				final String mailUsername = "redsocialeducativafiuba@gmail.com";
				final String mailPassword = "redsocialeducativa123";
		 
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
		 
				Session mailSession = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mailUsername, mailPassword);
					}
				  });
		 
				try {
		 
					Message message = new MimeMessage(mailSession);
					message.setFrom(new InternetAddress("redsocialeducativafiuba@gmail.com"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(email));
					message.setSubject("Activacion de usuario");
					message.setContent(
							"<p>Para activar su usuario haga click en el siguiente link:</p> "
							+ "<a href='http://localhost:8080/axis2/services/LoginAPIHelper/activateUserWithToken?activationToken=" + activationToken 
							+ "'>http://localhost:8080/axis2/services/LoginAPIHelper/activateUserWithToken?activationToken=" + activationToken 
							+ "</a>", "text/html");
		 
					Transport.send(message);
		 
					System.out.println("Done");
		 
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
				
			} catch (RemoteException excepcionDeInvocacion) {
				System.err.println(excepcionDeInvocacion.toString());
				
			}
		
			session.setSuccess(true);
		}

		return xmlutil.convertToXml(session, SessionResponse.class);
	}


	public String login(String username, String password) {

		// Verifica datos del usuario -> LLAMADA A WS DE INTEGRACION
		User user = new User();
		SessionResponse session = new SessionResponse();

		user.setUsername(username);

		// Llama a integracion y verifica la existencia del usuario.
		IntegracionStub integracion = null;
		IntegracionStub.SeleccionarDatos selectRequest = null;
		IntegracionStub.SeleccionarDatosResponse selectResponse = null;

		try {
			integracion = new IntegracionStub();
			selectRequest = new IntegracionStub.SeleccionarDatos();
			selectRequest.setXml(xmlutil.convertToXml(new UserWSForSelect(user), UserWSForSelect.class));
			selectResponse = integracion.seleccionarDatos(selectRequest);
			System.out.println("INTEGRACION RESPONSE: "+ selectResponse.get_return());
		} catch (RemoteException e) {
			System.err.println(e.toString());
		}
		
		

		if (!user.usuarioExistente(selectResponse.get_return())) {
			session.setSuccess(false);
			session.setReason("No existe el usuario");
		} else {
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
			String encryptedPassword = user.getPasswordFromXML(selectResponse.get_return());
			//String encryptedPassword = "SDadsad";
			
			Boolean success = false;
			if (passwordEncryptor.checkPassword(password, encryptedPassword)) {
				success = true;
			}
	
	
			// Si es correcta, genera token
			if (success) {
				String token = TokenManager.getInstance().getToken();
				TokenManager.getInstance().setToken(token, username);
				session.setAuthToken(token);
			}
	
			session.setSuccess(success);
		}
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
			session.setReason("La token es inválida");
		}

		session.setSuccess(success);

		return xmlutil.convertToXml(session, SessionResponse.class);
	}

	public String activateUser(String username) {

		// Se llama cuando el usuario confirma via email la cuenta
		// Setea como activado el usuario

		// Setea como activado al usuario
		User user = new User();
		user.setActivado(true);
		
		IntegracionStub integracion = null;
		IntegracionStub.ActualizarDatos selectRequest = null;
		IntegracionStub.ActualizarDatosResponse selectResponse = null;

		try {
			integracion = new IntegracionStub();
			selectRequest = new IntegracionStub.ActualizarDatos();
			selectRequest.setXml(xmlutil.convertToXml(new UserWSForSelect(user), UserWSForSelect.class));
			selectResponse = integracion.actualizarDatos(selectRequest);
		} catch (RemoteException e) {
			System.err.println(e.toString());
		}

		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		return xmlutil.convertToXml(session, SessionResponse.class);
	}
	
	public String activateUserWithToken(String activationToken) {
		
		String response = new String();
		
		String username = TokenManager.getInstance().activationTokenValid(activationToken);
		if (username != null) {
			// Existe el usuario lo activo
			String xmlResponse = this.activateUser(username);
			
			// Chequear respuesta
			// Si no hay error
			response = "Usuario " + username + " activado correctamente";
			TokenManager.getInstance().removeActivationToken(username);
			
		} else {
			response = "Error: No se encontro el nœmero de activacion";
		}
		
		return response;
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