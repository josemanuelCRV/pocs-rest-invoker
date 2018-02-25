package com.jmclabs.rest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import concurrent.Threads;

public class ThreadRunner extends BlockJUnit4ClassRunner {

	public ThreadRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	protected Description describeChild(FrameworkMethod method) {
		if (method.getAnnotation(Threads.class) != null && method.getAnnotation(Ignore.class) == null) {
			return describeThreadTest(method);
		}
		return super.describeChild(method);
	}

	private Description describeThreadTest(FrameworkMethod method) {
		int times = method.getAnnotation(Threads.class).value();

		Description description = Description.createSuiteDescription(testName(method) + " [" + times + " threads]",
				method.getAnnotations());

		for (int i = 1; i <= times; i++) {
			description.addChild(Description.createTestDescription(getTestClass().getJavaClass(),
					"[" + i + "] " + testName(method)));
		}
		return description;
	}

	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		Description description = describeChild(method);

		if (method.getAnnotation(Threads.class) != null && method.getAnnotation(Ignore.class) == null) {
			runThreadly(methodBlock(method), description, notifier);
		} else if (method.getAnnotation(Ignore.class) != null) {
			notifier.fireTestIgnored(description);
		} else {
			runLeaf(methodBlock(method), description, notifier);
		}
	}

	private void runThreadly(final Statement statement, Description description, final RunNotifier notifier) {
		// Lanzador de hilos
		ExecutorService es = Executors.newCachedThreadPool();

		for (final Description desc : description.getChildren()) {
			es.execute(new Runnable() {

				public void run() {
					// Ejecuta cada uno de los hilos
					runLeaf(statement, desc, notifier);
				}
			});
		}
		es.shutdown();

		try {
			// espera que terminen los test hasta un máx X mint.
			es.awaitTermination(30, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
