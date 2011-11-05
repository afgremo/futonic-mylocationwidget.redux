package org.beryl.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Base class for a Service that can run on pre-Eclair versions of Android. 
 */
public abstract class AbstractService extends Service {

	@Override
	public void onStart(Intent intent, int startId) {
		handleOnStartCommand(intent, 0, startId);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return handleOnStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/** New hook function for onStartCommand. */
	protected abstract int handleOnStartCommand(Intent intent, int flags, int startId);
}
