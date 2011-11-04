package org.beryl.concurrent;

import java.util.concurrent.*;

import org.beryl.diagnostics.Logger;

import com.futonredemption.mylocation.MyLocationBundle;

public class WatchdogTimer implements Runnable {

	Callable<MyLocationBundle> c = new Callable<MyLocationBundle>() {

		public MyLocationBundle call() throws Exception {
			MyLocationBundle bundle = new MyLocationBundle(null);
			try {
				Thread.sleep(5000);
			} catch(InterruptedException ex) {
				Logger.e(ex);
			}
			return bundle;
		}
		
	};
	public WatchdogTimer() {
		
	}
	public void run() {
		try {
			ExecutorService service = Executors.newSingleThreadExecutor();
			Future<MyLocationBundle> futureBundle = service.submit(c);
			service.shutdownNow();
			service.isTerminated();
			Logger.e(futureBundle.get().toString());
		} catch(Exception e) {
			
		}
	}
	
}
