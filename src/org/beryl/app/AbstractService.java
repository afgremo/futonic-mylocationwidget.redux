package org.beryl.app;

import org.beryl.concurrent.ReferenceCounter;

import android.content.Intent;

/**
 * Base class for a Service that can run on pre-Eclair versions of Android. 
 */
public abstract class AbstractService extends ServiceCompat {

	final ReferenceCounter stopCounter = new ReferenceCounter(new Runnable() {
		public void run() {
			stopSelf();
		}
	});
	
	@Override
	public int handleOnStartCommand(Intent intent, int flags, int startId) {
		stopCounter.up();
		return handleOnStartCommand2(intent, flags, startId);
	}
	
	/** New hook function for onStartCommand. */
	protected abstract int handleOnStartCommand2(Intent intent, int flags, int startId);
	
	protected void setRequestCompleted() {
		stopCounter.down();
	}
}
