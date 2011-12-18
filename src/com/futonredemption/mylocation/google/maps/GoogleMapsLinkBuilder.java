package com.futonredemption.mylocation.google.maps;

import java.util.Locale;

import android.location.Address;
import android.location.Location;
import android.net.Uri;

public class GoogleMapsLinkBuilder {

	private static final String GoogleMapsLinkPattern = "http://maps.google.com/maps?q=%f,+%f+(%s)&iwloc=A&hl=en&z=13";
	private final double latitude;
	private final double longitude;
	private String message = "";
	
	public GoogleMapsLinkBuilder(final double latitude, final double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public GoogleMapsLinkBuilder(final Location location) {
		this(location.getLatitude(), location.getLongitude());
	}
	
	public void setAddress(final Address address) {
		
	}
	
	public void setCustomMessage(final String message) {
		this.message = message;
	}
	
	public String build() {
		return String.format(Locale.ENGLISH, GoogleMapsLinkPattern, latitude, longitude, Uri.encode(message));
	}
}
