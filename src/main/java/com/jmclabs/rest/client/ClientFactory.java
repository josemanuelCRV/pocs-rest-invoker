package com.jmclabs.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.jmclabs.rest.filters.HandlerClientRequestFilter;


/**
 * 
 * Agrupación de conexiones a través de HttpClient
 * 
 * El tiempo de espera de lectura
 * 
 * El tiempo de espera de conexión
 * 
 * Número máximo de conexiones
 * 
 * Número máximo de conexiones por host (o ruta)
 * 
 * Soporte JSON
 * 
 * @author josem
 *
 */

public class ClientFactory {

	public static Client create() {

		ClientConfig clientConfig = defaultConfigurationClient();
		Client client = ClientBuilder.newClient(clientConfig);
		
		return client;
	}

	private static ClientConfig defaultConfigurationClient() {

		ClientConfig clientConfig = new ClientConfig();
		// values are in milliseconds
		clientConfig.property(ClientProperties.READ_TIMEOUT, 2000);
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 500);
		clientConfig.property(ClientProperties.ASYNC_THREADPOOL_SIZE, 20);
		
		// tell the config about the connection manager
		PoolingHttpClientConnectionManager connectionManager = poolingConnectionManager();
		clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);
		
		ApacheConnectorProvider connector = new ApacheConnectorProvider();
	    clientConfig.connectorProvider(connector);
				
		clientConfig.register(HandlerClientRequestFilter.class);
		
		return clientConfig;
	}

	private static PoolingHttpClientConnectionManager poolingConnectionManager() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(20);
		connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost("localhost")), 40);
		return connectionManager;
	}

}
