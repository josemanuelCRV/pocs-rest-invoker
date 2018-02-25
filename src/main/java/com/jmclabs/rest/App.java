package com.jmclabs.rest;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.ClientConfig;

import com.jmclabs.rest.client.Jersey2Client;
import com.jmclabs.rest.client.Jersey2ClientImpl;
import com.jmclabs.rest.client.Jersey2ClientImpl.Jersey2ClientBuilder;
import com.jmclabs.rest.client.MyClient;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) {
		System.out.println("Hello World!");

		
		 
		//MyClient client = new MyClient(2000, 500, 1000, null);
		
		
		
		Jersey2Client client = new Jersey2ClientImpl(0, 0, 0, null);
		Client c = client.getClient();
		
		
		
//		Jersey2ClientBuilder client = new Jersey2ClientImpl.Jersey2ClientBuilder();
//		client.withConnectionTimeout(0);
//		client.build();
		
		
	}
}
