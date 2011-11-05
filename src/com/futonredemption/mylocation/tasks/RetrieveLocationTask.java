package com.futonredemption.mylocation.tasks;

import org.beryl.diagnostics.Logger;
import org.beryl.location.LocationMonitor;
import org.beryl.location.ProviderSelectors;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class RetrieveLocationTask extends EventBasedContextAwareCallable<Location> {

	public RetrieveLocationTask(Context context) {
		super(context);
	}
	
	LocationMonitor monitor = null;
	
	@Override
	protected void onBeginTask() {
		Logger.w("Begin Location Retrieve");
		monitor = new LocationMonitor(context);
		final BestLocationListener bestLocationListener = new BestLocationListener(monitor);

		monitor.setProviderSelector(ProviderSelectors.AllFree);
		monitor.addListener(bestLocationListener);
		monitor.startListening();
		Logger.w("Listening");
	};

	@Override
	protected void onFinishTask() {
		Logger.w("Finishing");
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
				finishWithResult(getLocation());
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
