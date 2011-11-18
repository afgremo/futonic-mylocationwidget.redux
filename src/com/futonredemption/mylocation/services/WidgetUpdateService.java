package com.futonredemption.mylocation.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.beryl.app.AbstractService;
import org.beryl.diagnostics.Logger;

import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.tasks.DownloadStaticMapTask;
import com.futonredemption.mylocation.tasks.RetrieveAddressTask;
import com.futonredemption.mylocation.tasks.RetrieveLocationTask;
import com.futonredemption.mylocation.tasks.UpdateWidgetsTask;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;

public class WidgetUpdateService extends AbstractService {

	public static Intent getBeginFullUpdate(final Context context) {
		final Intent intent = new Intent(context, WidgetUpdateService.class);
		intent.putExtra("method", "FullUpdate");
		return intent;
	}
	
	public static void beginFullUpdate(Context context) {
		context.startService(getBeginFullUpdate(context));
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Logger.w("Service Destroy");
	}
	
	@Override
	protected int handleOnStartCommand(Intent intent, int flags, int startId) {
		final String method = intent.getStringExtra("method");
		
		if(method.equalsIgnoreCase("FullUpdate")) {
			beginFullUpdate();
		} else {
			setRequestCompleted();
		}
		
		return 0;
	}

	private void beginFullUpdate() {
		Thread.currentThread().setName("WidgetUpdateService");
		Logger.w("Starting WidgetUpdateService");
		
		Debug.waitForDebugger();
		
		final ExecutorService service = Executors.newSingleThreadExecutor();
		final MyLocationRetrievalState state = new MyLocationRetrievalState();
		UpdateWidgetsTask widgetUpdate;
		RetrieveLocationTask locationGet;
		RetrieveAddressTask addressGet;
		Future<MyLocationRetrievalState> future;
		DownloadStaticMapTask staticMapGet;
		
		widgetUpdate = new UpdateWidgetsTask(this, state);
		future = service.submit(widgetUpdate);
		
		locationGet = new RetrieveLocationTask(this, future);
		future = service.submit(locationGet);
		
		//widgetUpdate = new UpdateWidgetsTask(this, future);
		//future = service.submit(widgetUpdate);
		
		addressGet = new RetrieveAddressTask(this, future);
		future = service.submit(addressGet);
		
		staticMapGet = new DownloadStaticMapTask(this, future);
		future = service.submit(staticMapGet);
		
		widgetUpdate = new UpdateWidgetsTask(this, future);
		future = service.submit(widgetUpdate);

		RequestCompleted<Future<MyLocationRetrievalState>> serviceStopper = new RequestCompleted<Future<MyLocationRetrievalState>>(future, service);
		service.submit(serviceStopper);
	}
	
	class RequestCompleted<T extends Future<?>> implements Runnable {

		private final T future;
		private final ExecutorService service;
		
		public RequestCompleted(T future, ExecutorService service) {
			this.service = service;
			this.future = future;
		}
		
		public void run() {
			try {
				this.future.get();
			} catch (Exception e) {
				
			}
			service.shutdown();
			setRequestCompleted();
		}
	}
}
