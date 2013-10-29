package com.seguridadsesion.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SessionResponse {
 
	@XmlElement
    private String authToken;
	@XmlElement
    private String reason;
	@XmlElement
    private Boolean success;

    public SessionResponse(){}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
   
}
