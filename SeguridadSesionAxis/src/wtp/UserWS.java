package wtp;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "WS")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWS {
	
	public UserWS(){}
	
	public UserWS(ArrayList<User> userList) {
		this.userList = userList;
	}
	
	public UserWS(User user) {
		this.userList = new ArrayList<User>();
		this.userList.add(user);
	}

	@XmlElementWrapper(name = "list")
	@XmlElement(name = "Usuario")
	private ArrayList<User> userList;
	
	public ArrayList<User> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}
	
	public User getUser() {
		return this.userList.get(0);
	}
	
}
