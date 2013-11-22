package wtp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "WS")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWS {
	
	public UserWS(){}
	
	public UserWS(User user) {
		this.user = user;
	}

	@XmlElement(name = "Usuario")
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
