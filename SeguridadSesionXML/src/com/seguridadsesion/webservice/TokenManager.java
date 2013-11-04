package com.seguridadsesion.webservice;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class TokenManager {
	//SINGLETON
	
	private static TokenManager instance = null;
	
	private static ArrayList<String> validTokens;
	
	protected TokenManager() {
		validTokens = new ArrayList<String>();		
	}
	
	public static TokenManager getInstance() {
		if(instance == null) {
			instance = new TokenManager();
		}
		return instance;
	}
	
	public Boolean isTokenValid(String token) {
		return validTokens.contains(token);
	}
	
	public void setToken(String token) {
		validTokens.add(token);
	}
	
	public String getToken() {
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
	
	public Boolean removeToken(String token) {
		return validTokens.remove(token);
	}
	
}