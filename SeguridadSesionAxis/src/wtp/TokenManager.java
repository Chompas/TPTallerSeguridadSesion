package wtp;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

public class TokenManager {
	// SINGLETON

	private static TokenManager instance = null;

	private static HashMap<String, Token> validTokens;
	private static HashMap<String, String> activationTokens;

	protected TokenManager() {
		validTokens = new HashMap<String, Token>();
		activationTokens = new HashMap<String, String>();
	}

	public static TokenManager getInstance() {
		if (instance == null) {
			instance = new TokenManager();
		}
		return instance;
	}

	public String isTokenValid(String token) {
		Token tokenObject = validTokens.get(token);
		String username = "";
		if (tokenObject != null) {
			if (!tokenObject.hasExpired()) {

				username = tokenObject.getUsername();
				tokenObject.refreshToken();
			} else {
				removeToken(token);
			}
		}
		return username;
	}
	
	public String activationTokenValid(String token) {
		String username = activationTokens.get(token);
		return username;
	}

	public void setToken(String token, String username) {
		validTokens.put(token, new Token(username));
	}
	
	public void setActivationToken(String token, String username) {
		activationTokens.put(token, username);
	}

	public String getToken() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
	
	public String getActivationHashmap() {
		
		String response = "";
		
		for (String name: activationTokens.keySet()) {

            String key =name.toString();
            String value = activationTokens.get(name).toString(); 
            response += key + " " + value + "\n";
		}
		
		return response;
	}

	public Boolean removeToken(String token) {
		Token tokenObject = validTokens.remove(token);
		if (tokenObject != null) {
			return true;
		}
		return false;
	}
	
	public Boolean removeActivationToken(String token) {
		String username = activationTokens.remove(token);
		if (username != null) {
			return true;
		}
		return false;
	}

}
