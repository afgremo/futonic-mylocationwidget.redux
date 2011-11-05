package com.futonredemption.mylocation.services;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.beryl.app.AbstractService;
import org.beryl.diagnostics.Logger;

import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.tasks.RetrieveAddressTask;
import com.futonredemption.mylocation.tasks.RetrieveLocationTask;

import android.content.Intent;
import android.location.Location;

public class WidgetUpdateService extends AbstractService {

	@Override
	protected int handleOnStartCommand(Intent intent, int flags, int startId) {
		Thread.currentThread().setName("WidgetUpdateService");
		Logger.w("Starting WidgetUpdateService");
		ExecutorService service = Executors.newSingleThreadExecutor();
		RetrieveLocationTask locationGet = new RetrieveLocationTask(this);
		Future<Location> futureLocation = service.submit(locationGet);
		RetrieveAddressTask addressGet = new RetrieveAddressTask(this);
		addressGet.setLocation(futureLocation);
		Future<MyLocationBundle> futureBundle = service.submit(addressGet);
		service.shutdownNow();
		
		/*
		try {
			MyLocationBundle bundle = futureBundle.get();
			Logger.d(bundle.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return 0;
	}
}
