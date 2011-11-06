package com.futonredemption.mylocation;

import android.location.Address;
import android.location.Location;

public class MyLocationBundle {

	private Location location;
	private Address address;
	
	public MyLocationBundle(Location location) {
		this.location = location;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public boolean hasLocation() {
		return location != null;
	}
	
	public boolean hasAddress() {
		return address != null;
	}
	
}
