package com.futonredemption.mylocation;

import java.util.Locale;

import android.content.Context;
import android.location.Address;

public class BundleToViewModelAdapter {

	final Context context;
	final MyLocationBundle bundle;
	
	public BundleToViewModelAdapter(final Context context, MyLocationBundle bundle) {
		this.context = context;
		this.bundle = bundle;
	}
	
	public double getLatitude() {
		return bundle.getLocation().getLatitude();
	}
	
	public double getLongitude() {
		return bundle.getLocation().getLongitude();
	}
	
	public CharSequence getTitle() {
		CharSequence title = "";
		
		if(bundle.hasAddress()) {
			final Address address = bundle.getAddress();
			if(address.getMaxAddressLineIndex() > 0) {
				title = address.getAddressLine(0);
			}
		}
		else if (bundle.hasLocation()) {
			title = context.getText(R.string.coordinates);
		} else {
			title = context.getText(R.string.locating);
		}
		
		return title;
	}
	
	public CharSequence getDescription() {
		CharSequence description = "";
		
		if(bundle.hasAddress()) {
			final Address address = bundle.getAddress();
			if(address.getMaxAddressLineIndex() > 1) {
				description = address.getAddressLine(1);
			}
			else {
				description = address.getFeatureName();
			}
		}
		else if (bundle.hasLocation()){
			description = getOneLineCoordinates();
		} else {
			description = context.getText(R.string.finding_coordinates);
		}
		
		return description;
	}
	
	private CharSequence getOneLineCoordinates() {
		return String.format(Locale.ENGLISH, "Lat: %s Long: %s", getLatitude(), getLongitude());
	}
}
