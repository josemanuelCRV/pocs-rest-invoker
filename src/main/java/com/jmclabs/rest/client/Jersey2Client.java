package com.jmclabs.rest.client;

import javax.ws.rs.client.Client;

public interface Jersey2Client {

	Client getClient();

	/**
	 * Clean up resources.
	 */
	void destroyResources();

}
