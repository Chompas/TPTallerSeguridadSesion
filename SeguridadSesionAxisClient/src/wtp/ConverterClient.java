package wtp;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

public class ConverterClient {
	
	public static void main(String[] args) {
		
        try {
            LoginAPIHelperStub stub = new LoginAPIHelperStub();
            LoginAPIHelperStub.Suma request = new LoginAPIHelperStub.Suma();
            LoginAPIHelperStub.SumaResponse response = new LoginAPIHelperStub.SumaResponse();
            
            request.setX(7);
            request.setY(8);
            
            response = stub.suma(request);
            
            System.out.println("Suma: " + response.get_return());
            
            
    } catch (AxisFault e) {
            e.printStackTrace();
    } catch (RemoteException e) {
            e.printStackTrace();
    }
	}
}
