package com.seguridadsesion.webservice.client;

import com.seguridadsesion.webservice.ApiServiceHelper;
import com.seguridadsesion.webservice.ApiServiceHelperImplService;

public class JAXWSClient {

	public static void main(String[] args) {
		ApiServiceHelperImplService apiServiceHelper = new ApiServiceHelperImplService();  
		ApiServiceHelper apiHelper = apiServiceHelper.getApiServiceHelperImplPort();
		System.out.println(apiHelper.login("Sarasa"));

	}
}
