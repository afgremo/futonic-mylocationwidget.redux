package com.futonredemption.mylocation;

import java.util.Locale;

import android.content.Context;
import android.location.Address;

public class DataToViewModelAdapter {

	final Context context;
	final MyLocationRetrievalState state;
	
	public DataToViewModelAdapter(final Context context, MyLocationRetrievalState state) {
		this.context = context;
		this.state = state;
	}
	
	public MyLocationRetrievalState getState() {
		return state;
	}
	
	public double getLatitude() {
		return state.bundle.getLocation().getLatitude();
	}
	
	public double getLongitude() {
		return state.bundle.getLocation().getLongitude();
	}
	
	public CharSequence getTitle() {
		CharSequence title = new String();
		
		if(isLoading()) {
			title = context.getText(R.string.finding_location);
		} else if(isAvailable()) {
			if(state.bundle.hasAddress()) {
				final Address address = state.bundle.getAddress();
				if(address.getMaxAddressLineIndex() > 0) {
					title = address.getAddressLine(0);
				}
			}
			else if (state.bundle.hasLocation()) {
				title = context.getText(R.string.coordinates);
			} else {
				title = context.getText(R.string.locating);
			}
		} else {
			title = context.getText(R.string.error);
		}
		
		return title;
	}
	
	public CharSequence getDescription() {
		CharSequence description = new String();
		
		if(state.isCompleted()) {
			if(state.bundle.hasAddress()) {
				final Address address = state.bundle.getAddress();
				if(address.getMaxAddressLineIndex() > 1) {
					description = address.getAddressLine(1);
				}
				else {
					description = address.getFeatureName();
				}
			}
			else if (state.bundle.hasLocation()){
				description = getOneLineCoordinates();
			} else {
				description = context.getText(R.string.finding_location);
			}
		} else if (state.isLoading()) {
			if(state.bundle.hasLocation()) {
				description = context.getText(R.string.address);
			} else {
				description = context.getText(R.string.coordinates);
			}
		}
		
		return description;
	}
	
	private CharSequence getOneLineCoordinates() {
		return String.format(Locale.ENGLISH, "Lat: %s Long: %s", getLatitude(), getLongitude());
	}

	public boolean isAvailable() {
		return state.isCompleted();
	}
	
	public boolean isLoading() {
		return state.isLoading();
	}
}
