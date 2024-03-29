package com.futonredemption.mylocation.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.beryl.app.AbstractService;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.tasks.DownloadStaticMapTask;
import com.futonredemption.mylocation.tasks.LoadCloseEnoughDataTask;
import com.futonredemption.mylocation.tasks.LoadMostRecentLocationTask;
import com.futonredemption.mylocation.tasks.RetrieveAddressTask;
import com.futonredemption.mylocation.tasks.RetrieveLocationTask;
import com.futonredemption.mylocation.tasks.SaveLocationBundleTask;
import com.futonredemption.mylocation.tasks.SealLocationBundleTask;
import com.futonredemption.mylocation.tasks.ShortenUrlTask;
import com.futonredemption.mylocation.tasks.UpdateWidgetsTask;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

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
	
	final AtomicBoolean updateTaskIsRunning = new AtomicBoolean(false);
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Thread.currentThread().setName("UI Thread, Nothing");
		Debugging.w("Service Destroy");
	}
	
	@Override
	protected int handleOnStartCommand2(Intent intent, int flags, int startId) {
		final String method = intent.getStringExtra("method");
		
		if(method.equalsIgnoreCase("FullUpdate")) {
			beginFullUpdate();
		} else if (method.equalsIgnoreCase("SyncLatest")) {
			syncToLatestKnownLocation();
		} else {
			setRequestCompleted();
		}
		
		return START_NOT_STICKY;
	}

	private void syncToLatestKnownLocation() {
		if(updateTaskIsRunning.compareAndSet(false, true)) {
			Thread.currentThread().setName("WidgetUpdateService");
			Debugging.w("Starting WidgetUpdateService, syncToLatestKnownLocation");
	
			final ExecutorService service = createExecutorService();
			final MyLocationRetrievalState state = new MyLocationRetrievalState();
			Future<MyLocationRetrievalState> future;
			UpdateWidgetsTask widgetUpdate;
			LoadMostRecentLocationTask loadMostRecentLocation;
			
			widgetUpdate = new UpdateWidgetsTask(this, state);
			future = service.submit(widgetUpdate);
			
			loadMostRecentLocation = new LoadMostRecentLocationTask(this, future);
			future = service.submit(loadMostRecentLocation);
			
			future = addStandardMetadataTasks(service, future);
			
			widgetUpdate = new UpdateWidgetsTask(this, future);
			future = service.submit(widgetUpdate);
	
			RequestCompleted<Future<MyLocationRetrievalState>> serviceStopper = new RequestCompleted<Future<MyLocationRetrievalState>>(future, service);
			service.submit(serviceStopper);
		} else {
			setRequestCompleted();
		}
	}
	
	private ExecutorService createExecutorService() {
		return Executors.newSingleThreadExecutor();
		//return Executors.newCachedThreadPool();
	}
	
	private void beginFullUpdate() {
		if(updateTaskIsRunning.compareAndSet(false, true)) {
			Thread.currentThread().setName("WidgetUpdateService");
			Debugging.w("Starting WidgetUpdateService, beginFullUpdate");
			Debugging.breakpoint();
			
			final ExecutorService service = createExecutorService();
			final MyLocationRetrievalState state = new MyLocationRetrievalState();
			Future<MyLocationRetrievalState> future;
			UpdateWidgetsTask widgetUpdate;

			widgetUpdate = new UpdateWidgetsTask(this, state);
			future = service.submit(widgetUpdate);
			
			future = addStandardMetadataTasks(service, future);
			
			widgetUpdate = new UpdateWidgetsTask(this, future);
			future = service.submit(widgetUpdate);
	
			RequestCompleted<Future<MyLocationRetrievalState>> serviceStopper = new RequestCompleted<Future<MyLocationRetrievalState>>(future, service);
			service.submit(serviceStopper);
		} else {
			setRequestCompleted();
		}
	}
	
	public Future<MyLocationRetrievalState> addStandardMetadataTasks(ExecutorService service, Future<MyLocationRetrievalState> future) {
		RetrieveLocationTask locationGet;
		RetrieveAddressTask addressGet;
		DownloadStaticMapTask staticMapGet;
		SaveLocationBundleTask saveLocationBundle;
		SealLocationBundleTask sealLocationTask;
		LoadCloseEnoughDataTask locationCacheGet;
		ShortenUrlTask shortUrlGet;
		Future<MyLocationRetrievalState> futureAddress;
		Future<MyLocationRetrievalState> futureStaticMap;
		Future<MyLocationRetrievalState> futureShortUrl;
		
		locationGet = new RetrieveLocationTask(this, future);
		future = service.submit(locationGet);

		locationCacheGet = new LoadCloseEnoughDataTask(this, future);
		future = service.submit(locationCacheGet);
		
		addressGet = new RetrieveAddressTask(this, future);
		futureAddress = service.submit(addressGet);
		
		shortUrlGet = new ShortenUrlTask(this, futureAddress);
		futureShortUrl = service.submit(shortUrlGet);
		
		staticMapGet = new DownloadStaticMapTask(this, future);
		futureStaticMap = service.submit(staticMapGet);

		saveLocationBundle = new SaveLocationBundleTask(this, future);
		saveLocationBundle.addFuture(futureAddress);
		saveLocationBundle.addFuture(futureStaticMap);
		saveLocationBundle.addFuture(futureShortUrl);
		future = service.submit(saveLocationBundle);
		
		sealLocationTask = new SealLocationBundleTask(this, future);
		future = service.submit(sealLocationTask);

		return future;
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

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
