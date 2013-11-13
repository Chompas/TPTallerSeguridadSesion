package com.ws.cliente;

import java.rmi.RemoteException;

import com.ws.servidor.CalculadoraStub;

public class Test {

	public static void main(String[] args) {
		CalculadoraStub calculadora = null;
		CalculadoraStub.Suma request = null;
		CalculadoraStub.SumaResponse response = null;
		
		try {
			calculadora = new CalculadoraStub();
			request = new CalculadoraStub.Suma();
			
			request.setX(5);
			request.setY(10);
			
			response = calculadora.suma(request);
			
			System.out.println("La suma es: " + response.get_return());
			
		} catch (RemoteException e){
			System.err.println(e.toString());
		}
		
	}

}
