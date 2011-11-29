package com.futonredemption.mylocation.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.beryl.app.AbstractService;
import org.beryl.diagnostics.Logger;

import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.tasks.DownloadStaticMapTask;
import com.futonredemption.mylocation.tasks.RetrieveAddressTask;
import com.futonredemption.mylocation.tasks.RetrieveLocationTask;
import com.futonredemption.mylocation.tasks.SaveLocationBundleTask;
import com.futonredemption.mylocation.tasks.UpdateWidgetsTask;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;

public class WidgetUpdateService extends AbstractService {

	public static Intent getSyncToLatestKnownLocation(final Context context) {
		final Intent intent = new Intent(context, WidgetUpdateService.class);
		intent.putExtra("method", "SyncLatest");
		return intent;
	}
	
	public static Intent getBeginFullUpdate(final Context context) {
		final Intent intent = new Intent(context, WidgetUpdateService.class);
		intent.putExtra("method", "FullUpdate");
		return intent;
	}
	
	public static void beginSyncLatestKnownLocation(final Context context) {
		context.startService(getSyncToLatestKnownLocation(context));
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
		} else if (method.equalsIgnoreCase("SyncLatest")) {
			syncToLatestKnownLocation();
		} else {
			setRequestCompleted();
		}
		
		return 0;
	}

	private void syncToLatestKnownLocation() {
		// TODO Sync all widgets to latest known location. Otherwise obtain a new location.
		beginFullUpdate();
	}

	final AtomicBoolean UpdateTaskIsRunning = new AtomicBoolean(false);
	
	private void beginFullUpdate() {
		
		if(UpdateTaskIsRunning.compareAndSet(false, true)) {
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
			SaveLocationBundleTask saveLocationBundle;
			
			widgetUpdate = new UpdateWidgetsTask(this, state);
			future = service.submit(widgetUpdate);
			
			locationGet = new RetrieveLocationTask(this, future);
			future = service.submit(locationGet);
			
			addressGet = new RetrieveAddressTask(this, future);
			future = service.submit(addressGet);
			
			staticMapGet = new DownloadStaticMapTask(this, future);
			future = service.submit(staticMapGet);
			
			saveLocationBundle = new SaveLocationBundleTask(this, future);
			future = service.submit(saveLocationBundle);
			
			widgetUpdate = new UpdateWidgetsTask(this, future);
			future = service.submit(widgetUpdate);
	
			RequestCompleted<Future<MyLocationRetrievalState>> serviceStopper = new RequestCompleted<Future<MyLocationRetrievalState>>(future, service);
			service.submit(serviceStopper);
		} else {
			setRequestCompleted();
		}
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
