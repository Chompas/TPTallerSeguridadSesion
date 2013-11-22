package wtp;

import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis2.AxisFault;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ws.services.IntegracionStub;

public class ConverterClient {
	
	public static void main(String[] args) {
		
        try {
        	LoginAPIHelperStub api = new LoginAPIHelperStub();
        	LoginAPIHelperStub.RegisterUser request3 = new LoginAPIHelperStub.RegisterUser();
        	LoginAPIHelperStub.RegisterUserResponse response3 = new LoginAPIHelperStub.RegisterUserResponse();
        	
        	request3.setUsername("testUser");
        	request3.setApellido("test");
        	request3.setEmail("dsada");
        	request3.setFecha("11/10/1991");
        	request3.setNombres("test");
        	request3.setPadron("123-2");
        	request3.setPassword("123456");
        	request3.setRol(1);
        	
        	
        	
        	
        	response3 = api.registerUser(request3);
        	
        	System.out.println("REGISTER RESPONSE: " + response3.get_return());

            LoginAPIHelperStub.Login request = new LoginAPIHelperStub.Login();
            LoginAPIHelperStub.LoginResponse response = new LoginAPIHelperStub.LoginResponse();
            
            request.setUsername("testUser");
            request.setPassword("123456");
            
            response = api.login(request);
            
            System.out.println("LOGIN RESPONSE: " + response.get_return());
        	
            LoginAPIHelperStub.IsTokenValid request2 = new LoginAPIHelperStub.IsTokenValid();
            LoginAPIHelperStub.IsTokenValidResponse response2 = new LoginAPIHelperStub.IsTokenValidResponse();
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(response.get_return()));

            Document doc = builder.parse(is);
            Element element = doc.getDocumentElement();
            String token = element.getElementsByTagName("authToken").item(0).getTextContent();   
            
            
            request2.setAuthToken(token);
            
            response2 = api.isTokenValid(request2);
            
            System.out.println("ISTOKENVALID RESPONSE: " + response2.get_return());
            
        	
            LoginAPIHelperStub.Logout logoutRequest = new LoginAPIHelperStub.Logout();
            LoginAPIHelperStub.LogoutResponse logoutResponse = new LoginAPIHelperStub.LogoutResponse();
            
            logoutRequest.setAuthToken(token);
            logoutResponse = api.logout(logoutRequest);
            
            System.out.println("LOGOUT RESPONSE: "+ logoutResponse.get_return());
            
            LoginAPIHelperStub.IsTokenValid request4 = new LoginAPIHelperStub.IsTokenValid();
            LoginAPIHelperStub.IsTokenValidResponse response4 = new LoginAPIHelperStub.IsTokenValidResponse();
            
            request4.setAuthToken(token);
            
            response4 = api.isTokenValid(request4);
            
            System.out.println("ISTOKENVALID RESPONSE: "+response4.get_return());

            
	    } catch (AxisFault e) {
	            e.printStackTrace();
	    } catch (RemoteException e) {
	            e.printStackTrace();
	    } catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
