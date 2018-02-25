package com.jmclabs.rest.suit;

import static org.junit.Assert.*;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class JaxRsTimeoutTest extends JerseyTest {

	private final Client client = ClientBuilder.newBuilder().readTimeout(2000, TimeUnit.MILLISECONDS).build();

	@Path("/test")
	public static class TimeoutResource {
		@GET
		public String get() {
			return "GET";
		}

		@GET
		@Path("timeout")
		public String getTimeout() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "GET";
		}
	}

	@Override
	protected Application configure() {
		return new ResourceConfig(TimeoutTest.TimeoutResource.class);
	}

	@Override
	protected Client getClient() {
		return client;
	}

	@Test
	public void testFast() {
		Response r = target("test").request().get();
		assertEquals(200, r.getStatus());
		assertEquals("GET", r.readEntity(String.class));
	}

	@Test
	public void testSlow() {
		try {
			target("test/timeout").request().get();
			fail("Timeout expected.");
		} catch (ProcessingException e) {
			if (!(e.getCause() instanceof SocketTimeoutException)) {
				e.printStackTrace();
				fail();
			}
		}
	}
}
