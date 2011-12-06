package com.futonredemption.mylocation.tasks;

import java.util.List;
import java.util.concurrent.Future;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;

public class RetrieveAddressTask extends AbstractMyLocationTask {

	private static final int NUM_ATTEMPTS = 4;
	
	public RetrieveAddressTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public RetrieveAddressTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		if(state.hasLocation() && ! state.hasAddress()) {
			final Location location = state.getLocation();
			try {
				final Geocoder coder = new Geocoder(context);
				Address address = null;
				
				for(int i = 0; i < NUM_ATTEMPTS && address == null; i++) {
					address = tryGetAddress(coder, location);
				}
				state.setAddress(address);
			} catch(Exception e) {
				state.setError(e);
			}
		}
	}

	private Address tryGetAddress(Geocoder coder, Location location) {
		List<Address> addresses = null;
		
		try {
			addresses = coder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
		} catch (Exception e) {
			Debugging.e(e);
		}
		
		if(addresses == null || addresses.size() == 0) {
			return null;
		} else {
			return addresses.get(0);
		}
	}
}
