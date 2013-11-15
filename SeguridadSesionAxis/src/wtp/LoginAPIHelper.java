package wtp;


public class LoginAPIHelper {
	
	public String suma(int x, int y) {
		SessionResponse session = new SessionResponse();
		session.setSuccess(true);
		session.setAuthToken("2222222");
		XmlUtil xmlutil = new XmlUtil();
		return xmlutil.convertToXml(session, SessionResponse.class);
	}
	
}