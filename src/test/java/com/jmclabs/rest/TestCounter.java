package com.jmclabs.rest;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;

@RunWith(ConcurrentTestRunner.class)
public class TestCounter {

	private Counter counter = new Counter();

	@Test
	public void addOne() {
		counter.addOne();

	}

	@After
	public void testCount() {
		assertEquals("4 Threads running addOne in parallel should lead to 4", 4, counter.getCount());
	}

	@Test
	public void iteraTest() {
		for (int i = 0; i < 10; i++) {
			test();
		}
	}

	private void test() {
		System.out.println("Probando el test");
	}

	// ******************

	public class Counter {

		private volatile int count = 0;
		// private final AtomicInteger count= new AtomicInteger();

		public void addOne() {
			count++;
		}

		public int getCount() {
			return count;
		}
	}

}
