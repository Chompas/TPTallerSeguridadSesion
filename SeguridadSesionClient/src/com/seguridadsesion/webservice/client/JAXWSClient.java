package com.seguridadsesion.webservice.client;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.seguridadsesion.webservice.ApiServiceHelper;
import com.seguridadsesion.webservice.ApiServiceHelperImplService;
import com.seguridadsesion.webservice.Session;

public class JAXWSClient {

	public static void main(String[] args) {
		ApiServiceHelperImplService apiServiceHelper = new ApiServiceHelperImplService();  
		ApiServiceHelper apiHelper = apiServiceHelper.getApiServiceHelperImplPort();
		Session session = (apiHelper.login("sarasa"));
		
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Session.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			//Output pretty print using Marshaller
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			jaxbMarshaller.marshal(session, System.out);
			//jaxbMarshaller.marshal( new JAXBElement(new QName("uri","local"), SessionResponse.class, session ), System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
