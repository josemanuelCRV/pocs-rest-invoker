package com.jmclabs.rest.filters;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class HandlerClientRequestFilter implements ClientRequestFilter{

	static Logger log = LoggerFactory.getLogger(HandlerClientRequestFilter.class);
	boolean isDebugEnable = log.isDebugEnabled();
	
	public void filter(ClientRequestContext clientReqCtx) throws IOException {
		if (isDebugEnable) {
			log.debug("HandlerClientRequestFilter-[IN]-filer()");
		}
		
		// Adding some headers
		clientReqCtx.getHeaders().add("cli-Header-1", "value-1");
		clientReqCtx.getHeaders().add("cli-Header-2", "value-1");

		/**
		 * Print all headers that will go inside client request before get in to
		 * ContainerRequest (servlet-side)
		 */
		try {
			String method = clientReqCtx.getMethod();
			URI uri = clientReqCtx.getUri();
			MultivaluedMap<String, Object> headers = clientReqCtx.getHeaders();
			System.out.printf("Sending %s to: %s\n", method, uri);
			log.debug("Sending {} to: {}\n", method, uri);
			System.out.printf("Headers:\n");
			log.debug("Headers:\n");
			for (String key : headers.keySet()) {
				System.out.printf(" %s: %s\n", key, headers.getFirst(key));
				log.debug(" {}: {}\n", key, headers.getFirst(key));
			}
		} catch (Exception e) {
			log.error("HandlerClientRequestFilter-[ERROR] {}", e);
			System.out.printf("Error - HandlerClientRequestFilter: %s", e);
		}

	}

}
