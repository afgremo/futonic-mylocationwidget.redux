package com.futonredemption.mylocation;

import android.location.Address;
import android.location.Location;

/** Stores the state of the Location Retrieval process from the WidgetUpdateService. */
public class MyLocationRetrievalState {

	private final MyLocationBundle bundle;
	private Exception error = null;
	private boolean staleLocationIndicator = false;
	
	public MyLocationRetrievalState() {
		this.bundle = new MyLocationBundle();
	}
	
	public MyLocationRetrievalState(MyLocationBundle bundle) {
		this.bundle = bundle;
	}
	
	public Exception getError() {
		return error;
	}
	
	public boolean hasError() {
		return error != null;
	}
	
	public void setError(Exception error) {
		this.error = error;
	}
	
	public boolean isError(Class<?> errorClass) {
		boolean result = false;
		
		if(this.error != null) {
			Class<?> errorType = this.error.getClass();
			result = errorType.isAssignableFrom(errorClass);
		}
		return result;
	}
	
	public boolean isLoading() {
		return ! hasError() && !(bundle.hasLocation() && bundle.hasAddress());
	}
	
	public boolean isCompleted() {
		return ! isLoading() && ! hasError();
	}
	
	public int getFindingLocationState() {
		if(! bundle.hasLocation()) {
			return R.string.coordinates;
		}
		if(! bundle.hasAddress()) {
			return R.string.address;
		}
		
		return R.string.unknown;
	}

	public Location getLocation() {
		return bundle.getLocation();
	}
	
	public Address getAddress() {
		return bundle.getAddress();
	}
	
	public boolean hasLocation() {
		return bundle.hasLocation();
	}
	
	public boolean hasAddress() {
		return bundle.hasAddress();
	}

	public void setLocation(Location location) {
		this.bundle.setLocation(location);
	}
	
	public void setAddress(Address address) {
		this.bundle.setAddress(address);
	}

	public void setStateLocationIndicator(boolean isStale) {
		this.staleLocationIndicator = isStale;
	}
	
	public boolean isLocationStale() {
		return staleLocationIndicator;
	}

	public MyLocationBundle getLocationBundle() {
		return this.bundle;
	}
}
