package com.jmclabs.rest.suit;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import org.glassfish.jersey.test.JerseyTest;


public class TimeoutTest extends JerseyTest {
	
	
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

    protected Application configure() {
        return new ResourceConfig(TimeoutResource.class);
    }

    protected void configureClient(ClientConfig config) {
        config.property(ClientProperties.READ_TIMEOUT, 2000);
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
            if (!(e.getCause() instanceof IOException)) {
                e.printStackTrace();
                fail();
            }
        }
    }
}
