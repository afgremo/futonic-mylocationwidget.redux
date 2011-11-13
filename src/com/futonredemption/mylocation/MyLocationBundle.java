package com.futonredemption.mylocation;

import android.location.Address;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * Data Transfer Object for MyLocation Elements
 * @author jeremyje
 *
 */
public class MyLocationBundle implements Parcelable {

	private Location location = null;
	private Address address = null;
	
	public MyLocationBundle() {
	}
	
	public MyLocationBundle(Location location) {
		this.location = location;
	}

	public MyLocationBundle(Parcel in) {
		readFromParcel(in);
	}

	public void setLocation(Location location) {
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
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
    	dest.writeParcelable(location, flags);
    	dest.writeParcelable(address, flags);
    }

    public void readFromParcel(Parcel in) {
    	location = in.readParcelable(null);
    	address = in.readParcelable(null);
    }

    public static final Parcelable.Creator<MyLocationBundle> CREATOR = new Parcelable.Creator<MyLocationBundle>() {
        public MyLocationBundle createFromParcel(Parcel in) {
            return new MyLocationBundle(in);
        }

        public MyLocationBundle[] newArray(int size) {
            return new MyLocationBundle[size];
        }
    };
}
