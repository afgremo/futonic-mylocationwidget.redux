package com.futonredemption.mylocation.tasks;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.beryl.diagnostics.Logger;
import org.beryl.location.LocationMonitor;
import org.beryl.location.ProviderSelectors;

import com.futonredemption.mylocation.MyLocationRetrievalState;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class RetrieveLocationTask extends EventBasedContextAwareCallable<MyLocationRetrievalState> {

	final Future<MyLocationRetrievalState> future;
	public RetrieveLocationTask(Context context, Future<MyLocationRetrievalState> future) {
		super(context);
		this.future = future;
	}

	LocationMonitor monitor = null;
	
	@Override
	protected void onBeginTask() {
		try {
			MyLocationRetrievalState state = future.get();
			this.result = state;
			monitor = new LocationMonitor(context);
			final BestLocationListener bestLocationListener = new BestLocationListener(monitor);

			monitor.setProviderSelector(ProviderSelectors.AllFree);
			monitor.addListener(bestLocationListener);
			monitor.startListening();
		} catch (InterruptedException e) {
			finishWithError(e);
		} catch (ExecutionException e) {
			finishWithError(e);
		}
	};

	@Override
	protected void onFinishTask() {
		if(monitor != null) {
			monitor.stopListening();
		}
	}
	
	class BestLocationListener implements LocationListener {

		private final float DESIRED_ACCURACY = 50.0f;
		private Location baselineLocation = null;
		private Location bestLocation = null;
		
		private final Object lock = new Object();
		
		public BestLocationListener(LocationMonitor monitor) {
			this.baselineLocation = monitor.getBestStaleLocation();
		}

		public Location getLocation() {
			Location location;
			
			synchronized(lock) {
				if(bestLocation != null) {
					location = bestLocation;
				} else {
					location = baselineLocation;
				}
			}
			
			return location;
		}
		
		public void onLocationChanged(final Location location) {
			Logger.w("Location Updated");
			synchronized(lock) {
				if(bestLocation == null || bestLocation.getAccuracy() > location.getAccuracy()) {
					bestLocation = location;
				}
			}
			
			if(location.getAccuracy() <= DESIRED_ACCURACY) {
				result.bundle.setLocation(getLocation());
				finishWithResult(result);
			}
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}
