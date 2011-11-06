package org.beryl.app;

import org.beryl.concurrent.ReferenceCounter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Base class for a Service that can run on pre-Eclair versions of Android. 
 */
public abstract class AbstractService extends Service {

	final ReferenceCounter stopCounter = new ReferenceCounter(new Runnable() {
		public void run() {
			stopSelf();
		}
	});
	
	@Override
	public void onStart(Intent intent, int startId) {
		handleOnStartCommand(intent, 0, startId);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		stopCounter.up();
		return handleOnStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/** New hook function for onStartCommand. */
	protected abstract int handleOnStartCommand(Intent intent, int flags, int startId);
	
	protected void setRequestCompleted() {
		stopCounter.down();
	}
}
