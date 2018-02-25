package com.jmclabs.rest.client;

import javax.ws.rs.client.Client;

public interface IMyClient {

	Client getClient();
	
	void destroyresources();
	
	
	
	
}
