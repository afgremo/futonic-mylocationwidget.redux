package com.futonredemption.mylocation.tasks;

import org.beryl.diagnostics.Logger;
import org.beryl.location.LocationMonitor;
import org.beryl.location.ProviderSelectors;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class RetrieveLocationTask extends ContextAwareCallable<Location> {

	public RetrieveLocationTask(Context context) {
		super(context);
	}
	
	public Location call() throws Exception {
		
		final LocationMonitor monitor = new LocationMonitor(context);
		final BestLocationListener bestLocationListener = new BestLocationListener(monitor);

		try {
			monitor.setProviderSelector(ProviderSelectors.AllFreeEnabledOnly);
			monitor.addListener(bestLocationListener);
			monitor.startListening();
			
			monitor.wait();
		} catch(InterruptedException e) {
			Logger.e(e);
		} catch(Exception ex) {
			Logger.e(ex);
		} finally {
			monitor.stopListening();
		}

		return bestLocationListener.getLocation();
	}

	class BestLocationListener implements LocationListener {

		private final float DESIRED_ACCURACY = 50.0f;
		private Location baselineLocation = null;
		private Location bestLocation = null;
		private final LocationMonitor monitor;
		
		private final Object lock = new Object();
		
		public BestLocationListener(LocationMonitor monitor) {
			this.monitor = monitor; 
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
			synchronized(lock) {
				if(bestLocation == null || bestLocation.getAccuracy() > location.getAccuracy()) {
					bestLocation = location;
				}
			}
			
			if(location.getAccuracy() <= DESIRED_ACCURACY) {
				monitor.notifyAll();
			}
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};
}
