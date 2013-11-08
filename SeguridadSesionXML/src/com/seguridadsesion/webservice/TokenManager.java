package com.seguridadsesion.webservice;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

public class TokenManager {
	// SINGLETON

	private static TokenManager instance = null;

	private static HashMap<String, Token> validTokens;

	protected TokenManager() {
		validTokens = new HashMap<String, Token>();
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

	public void setToken(String token, String username) {
		validTokens.put(token, new Token(username));
	}

	public String getToken() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}

	public Boolean removeToken(String token) {
		Token tokenObject = validTokens.remove(token);
		if (tokenObject != null) {
			return true;
		}
		return false;
	}

}