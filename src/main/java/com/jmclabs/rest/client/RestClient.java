package com.jmclabs.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.jmclabs.rest.filters.HandlerClientRequestFilter;

public class RestClient {

	public Client create() {

		ClientConfig clientConfig = defaultConfigurationClient();

		// clientConfig.register(new MyContainerRequestFilter());
		// clientConfig.register(new MyClientRequestFilter());
		// clientConfig.register(new CheckRequestFilter());

		// PoolingClientConnectionManager connectionManager = new
		// PoolingClientConnectionManager();
		// connectionManager.setMaxTotal(100);
		// connectionManager.setDefaultMaxPerRoute(20);
		// connectionManager.setMaxPerRoute(new HttpRoute(new
		// HttpHost("localhost")), 40);

		// clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER,
		// connectionManager);

		// ApacheConnector connector = new ApacheConnector(clientConfig);
		// clientConfig.connector(connector);

		Client client = ClientBuilder.newClient(clientConfig);
		client.register(JacksonFeature.class);

		return client;
	}

	/**
	 * @return
	 */
	private ClientConfig defaultConfigurationClient() {
		ClientConfig clientConfig = new ClientConfig();
		// values are in milliseconds
		clientConfig.property(ClientProperties.READ_TIMEOUT, 2000);
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 500);
		clientConfig.property(ClientProperties.ASYNC_THREADPOOL_SIZE, 20);
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

	public ClientConfig CustomConfigurationClient(int read_timeout, int connect_timeout, int async_threadpool_size) {


		ClientConfig clientConfig = new ClientConfig();

		return clientConfig;

	}

}
