package com.futonredemption.mylocation.tasks;

import java.util.List;
import java.util.concurrent.Future;

import org.beryl.diagnostics.Logger;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.futonredemption.mylocation.MyLocationBundle;

public class RetrieveAddressTask extends AbstractMyLocationTask {

	private static final int NUM_ATTEMPTS = 4;
	
	Future<Location> futureLocation = null;
	public RetrieveAddressTask(Context context, Future<MyLocationBundle> bundle) {
		super(context, bundle);
	}

	@Override
	protected void appendLocationData(MyLocationBundle bundle) {
		final Location location = bundle.getLocation();
		try {
			Logger.w("Starting address finding.");
			final Geocoder coder = new Geocoder(context);
			Address address = null;
			
			for(int i = 0; i < NUM_ATTEMPTS && address == null; i++) {
				address = tryGetAddress(coder, location);
			}
			bundle.setAddress(address);
		} finally {
			Logger.w("Address Finishing: ");
			Logger.w(bundle.getAddress().toString());
		}
	}

	private Address tryGetAddress(Geocoder coder, Location location) {
		List<Address> addresses = null;
		
		try {
			addresses = coder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
		} catch (Exception e) {
			Logger.e(e);
		}
		
		if(addresses == null || addresses.size() == 0) {
			return null;
		} else {
			return addresses.get(0);
		}
	}
}
