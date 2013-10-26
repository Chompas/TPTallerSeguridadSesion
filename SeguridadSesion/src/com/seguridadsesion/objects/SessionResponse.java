package com.seguridadsesion.objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "session")
public class SessionResponse {

	    @XmlElement
	    public String authToken;
	    @XmlElement
	    public String reason;
	    @XmlElement
	    public Boolean success;
	    
	    
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
