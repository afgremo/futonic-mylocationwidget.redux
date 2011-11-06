package com.futonredemption.mylocation.tasks;

import org.beryl.diagnostics.Logger;
import org.beryl.location.LocationMonitor;
import org.beryl.location.ProviderSelectors;

import com.futonredemption.mylocation.MyLocationBundle;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class RetrieveLocationTask extends EventBasedContextAwareCallable<MyLocationBundle> {

	public RetrieveLocationTask(Context context) {
		super(context);
	}
	
	LocationMonitor monitor = null;
	
	@Override
	protected void onBeginTask() {
		monitor = new LocationMonitor(context);
		final BestLocationListener bestLocationListener = new BestLocationListener(monitor);

		monitor.setProviderSelector(ProviderSelectors.AllFree);
		monitor.addListener(bestLocationListener);
		monitor.startListening();
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
				finishWithResult(new MyLocationBundle(getLocation()));
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
