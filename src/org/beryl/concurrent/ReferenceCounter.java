package org.beryl.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

public class ReferenceCounter {

	final Runnable onZeroCountDelegate;
	final AtomicInteger counter = new AtomicInteger(0);
	
	public ReferenceCounter(Runnable onZeroCountDelegate) {
		this.onZeroCountDelegate = onZeroCountDelegate;
	}
	
	public void up() {
		counter.incrementAndGet();
	}
	
	public void down() {
		final int count = counter.decrementAndGet();
		if(count <= 0) {
			onZeroCountDelegate.run();
		}
	}
}
