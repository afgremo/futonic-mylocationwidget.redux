package com.futonredemption.mylocation;

import android.location.Address;
import android.location.Location;

/** Stores the state of the Location Retrieval process from the WidgetUpdateService. */
public class MyLocationRetrievalState {

	private final MyLocationBundle bundle;
	private Exception error = null;
	private boolean staleLocationIndicator = false;
	private boolean isModified = false;
	private boolean isNew = true;
	
	private boolean isSealed = false;
	
	public MyLocationRetrievalState() {
		this.bundle = new MyLocationBundle();
	}
	
	public MyLocationRetrievalState(MyLocationBundle bundle) {
		this.bundle = bundle;
		this.isNew = false;
	}
	
	public void copyFrom(MyLocationBundle bundle) {
		this.bundle.setAddress(bundle.getAddress());
		this.bundle.setLocation(bundle.getLocation());
		this.bundle.setStaticMap(bundle.getStaticMap());
		this.isModified = false;
		this.isNew = false;
	}
	
	public Exception getError() {
		return error;
	}
	
	public boolean isNew() {
		return this.isNew;
	}
	
	public boolean isModified() {
		return this.isModified;
	}
	
	public boolean isSealed() {
		return this.isSealed;
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
		return ! hasError() && ! isSealed;
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
	
	public StaticMap getStaticMap() {
		return bundle.getStaticMap();
	}
	
	public boolean hasLocation() {
		return bundle.hasLocation();
	}
	
	public boolean hasAddress() {
		return bundle.hasAddress();
	}

	public boolean hasStaticMap() {
		return this.bundle.hasStaticMap();
	}
	
	public void setLocation(Location location) {
		this.bundle.setLocation(location);
		this.isModified = true;
	}
	
	public void setAddress(Address address) {
		this.bundle.setAddress(address);
		this.isModified = true;
	}

	public void setStaticMap(StaticMap staticMap) {
		this.bundle.setStaticMap(staticMap);
		this.isModified = true;
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

	/** Indicate that no more data will be written to the bundle's metadata. */
	public void seal() {
		this.isSealed = true;
	}
}
