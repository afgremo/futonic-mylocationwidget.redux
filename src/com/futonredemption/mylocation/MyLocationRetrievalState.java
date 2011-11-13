package com.futonredemption.mylocation;

public class MyLocationRetrievalState {

	public MyLocationBundle bundle = new MyLocationBundle();
	public Exception error = null;

	public Exception getError() {
		return error;
	}
	
	public boolean hasError() {
		return error != null;
	}

	public boolean isLoading() {
		return !(bundle.hasLocation() && bundle.hasAddress());
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
}
