package wtp;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

public class ConverterClient {
	
	public static void main(String[] args) {
		
        try {
        	LoginAPIHelperStub stub = new LoginAPIHelperStub();
            LoginAPIHelperStub.Login request = new LoginAPIHelperStub.Login();
            LoginAPIHelperStub.LoginResponse response = new LoginAPIHelperStub.LoginResponse();
            
            request.setUsername("javi");
            request.setPassword("123456");
            
            response = stub.login(request);
            
            System.out.println(response.get_return());
        	
            LoginAPIHelperStub stub2 = new LoginAPIHelperStub();
            LoginAPIHelperStub.IsTokenValid request2 = new LoginAPIHelperStub.IsTokenValid();
            LoginAPIHelperStub.IsTokenValidResponse response2 = new LoginAPIHelperStub.IsTokenValidResponse();
            
            request2.setAuthToken("rn3si0cp56tcc7e0ja32ujsj6o");
            
            response2 = stub2.isTokenValid(request2);
            
            System.out.println(response2.get_return());
            
            
    } catch (AxisFault e) {
            e.printStackTrace();
    } catch (RemoteException e) {
            e.printStackTrace();
    }
	}
}
