package org.beryl.app;

import android.content.Intent;

public class MultithreadedQueueService extends AbstractService {

	static class Request {
		
	}
	
	@Override
	protected int handleOnStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return START_STICKY;
	}

}
