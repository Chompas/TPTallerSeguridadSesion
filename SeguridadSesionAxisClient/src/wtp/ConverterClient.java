package wtp;

import java.rmi.RemoteException;
import wtp.ConverterStub;
import org.apache.axis2.AxisFault;

public class ConverterClient {
	
	public static void main(String[] args) {
		
        try {
            ConverterStub stub = new ConverterStub();
            ConverterStub.Suma request = new ConverterStub.Suma();
            ConverterStub.SumaResponse response = new ConverterStub.SumaResponse();
            
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
