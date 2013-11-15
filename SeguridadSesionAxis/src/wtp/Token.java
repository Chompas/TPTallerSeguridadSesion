package wtp;

import java.sql.Timestamp;
import java.util.Date;

public class Token {
	private String username;

	private Timestamp expirationTime;

	private int refreshTime = 600000; // 10 minutos

	public Token(String username) {
		this.username = username;
		Date date = new Date();
		this.expirationTime = new Timestamp(date.getTime() + refreshTime);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void refreshToken() {
		Date date = new Date();
		this.expirationTime = new Timestamp(date.getTime() + refreshTime);
	}

	public Boolean hasExpired() {
		Date date = new Date();
		Timestamp actualTime = new Timestamp(date.getTime());
		return actualTime.after(this.expirationTime);
	}
}
