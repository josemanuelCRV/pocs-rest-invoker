package com.jmclabs.rest.suit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.jmclabs.rest.ThreadRunner;
import com.jmclabs.rest.client.ClientFactory;
import com.jmclabs.rest.client.Jersey2ClientImpl.Jersey2ClientBuilder;

import concurrent.Threads;

@RunWith(ThreadRunner.class)
public class MyTest {
 
    @Test
    @Threads(10)
    public void test() {
        System.out.println("Probando el test");
//        MyTest.cliente();
        clienteBuiderTest();
       
    }
    
    public static void cliente(){
    	Client client = ClientFactory.create();
    	WebTarget target = client.target("http://localhost:8080/TestJersey2/rest/").path("test/get");
		String res = target.request().header("Client-Name", "jose-1").get().readEntity(String.class);
		System.out.println(res);    	
    }
    
    private static void clienteBuiderTest(){
    	Jersey2ClientBuilder clientBuilder = new Jersey2ClientBuilder();
		Client client =  clientBuilder.withClientName("MyClientX")
				.withConnectionIdleTimeout(500)
				.withConnectionTimeout(500)
				.withMaxConnectionsPerHost(2)
				.withMaxTotalConnections(100)
				.withReadTimeout(2000)
				.withUserAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				.build()
				.getClient();
		
		WebTarget target = client.target("http://localhost:8080/TestJersey2/rest/").path("test/get");
		String res = target.request().header("Client-Name", "jose-1").get().readEntity(String.class);
		System.out.println(res);
    }
    
}
