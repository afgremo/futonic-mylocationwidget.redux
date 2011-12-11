package com.futonredemption.mylocation;

import com.google.android.maps.GeoPoint;

import android.location.Address;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * Data Transfer Object for MyLocation Elements
 *
 */
public class MyLocationBundle implements Parcelable {

	private Location location = null;
	private Address address = null;
	private StaticMap staticMap = null;
	
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
	
	public void setStaticMap(StaticMap staticMap) {
		this.staticMap = staticMap;
	}

	public Location getLocation() {
		return location;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public StaticMap getStaticMap() {
		return this.staticMap;
	}
	
	public boolean hasLocation() {
		return location != null;
	}
	
	public boolean hasAddress() {
		return address != null;
	}
	
	public boolean hasStaticMap() {
		return staticMap != null;
	}
	
	public GeoPoint toGeoPoint() {
		int latitude = (int) (location.getLatitude() * 1E6);
		int longitude = (int) (location.getLongitude() * 1E6);
		
		GeoPoint point = new GeoPoint(latitude, longitude);
		return point;
	}
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
    	dest.writeParcelable(location, flags);
    	dest.writeParcelable(address, flags);
    	dest.writeParcelable(staticMap, flags);
    }

    public void readFromParcel(Parcel in) {
    	location = in.readParcelable(null);
    	address = in.readParcelable(null);
    	staticMap = in.readParcelable(getClass().getClassLoader());
    }

    public static final Parcelable.Creator<MyLocationBundle> CREATOR = new Parcelable.Creator<MyLocationBundle>() {
        public MyLocationBundle createFromParcel(Parcel in) {
            return new MyLocationBundle(in);
        }

        public MyLocationBundle[] newArray(int size) {
            return new MyLocationBundle[size];
        }
    };

	public boolean hasAllStaticMaps() {
		if(this.hasStaticMap()) {
			return staticMap.hasAllMaps();
		}
		return false;
	}

	public boolean hasAnyStaticMap() {
		if(hasStaticMap()) {
			return staticMap.hasAnyStaticMap();
		} else {
			return false;
		}
	}
	
	public void fillFrom(MyLocationBundle originalBundle) {
		if(! hasLocation() && originalBundle.hasLocation()) {
			setLocation(originalBundle.getLocation());
		}
		
		if(! hasAddress() && originalBundle.hasAddress()) {
			setAddress(originalBundle.getAddress());
		}
		
		if(! hasAllStaticMaps() && originalBundle.hasAnyStaticMap()) {
			if(hasStaticMap()) {
				this.staticMap.fillFrom(originalBundle.getStaticMap());
			} else {
				setStaticMap(originalBundle.getStaticMap());
			}
		}
	}

	public boolean hasSmallStaticMap() {
		if(hasStaticMap()) {
			return staticMap.hasSmallMap();
		} else {
			return false;
		}
	}
	
	public boolean hasMediumStaticMap() {
		if(hasStaticMap()) {
			return staticMap.hasMediumMap();
		} else {
			return false;
		}
	}
	
	public boolean hasLargeStaticMap() {
		if(hasStaticMap()) {
			return staticMap.hasLargeMap();
		} else {
			return false;
		}
	}
}
