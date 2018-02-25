package com.jmclabs.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jmclabs.rest.filters.HandlerClientRequestFilter;

public class MyClient  implements IMyClient{
	
	private static final Logger s_logger = LoggerFactory.getLogger(MyClient.class);
	ClientConfig jerseyClientConfig;
	private final Client apacheHttpClient;

	public MyClient (int connectionTimeout, int readTimeout, final int connectionIdleTimeout,
			ClientConfig clientConfig){
		
		try {
			jerseyClientConfig = clientConfig;
			// jerseyClientConfig.register(DiscoveryJerseyProvider.class);
			jerseyClientConfig.connectorProvider(new ApacheConnectorProvider());
			jerseyClientConfig.property(ClientProperties.CONNECT_TIMEOUT, connectionTimeout);
			jerseyClientConfig.property(ClientProperties.READ_TIMEOUT, readTimeout);
			jerseyClientConfig.register(HandlerClientRequestFilter.class);
			apacheHttpClient = ClientBuilder.newClient(jerseyClientConfig);
				
		} catch (Throwable e) {
			throw new RuntimeException("Cannot create Jersey2 client", e);
		}
		
	 }
	
	
	
	
	
	public Client getClient() {
		return apacheHttpClient;
	}

	public void destroyresources() {
		
	}
	
	
	
	
	
	
	
	
}
