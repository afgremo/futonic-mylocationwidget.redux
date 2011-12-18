package com.futonredemption.mylocation;

import java.util.Locale;

import com.futonredemption.mylocation.google.maps.GoogleMapsLinkBuilder;
import com.futonredemption.mylocation.persistence.MyLocationBundleRecord;

import android.location.Address;
import android.location.Location;

/** Stores the state of the Location retrieval process from the WidgetUpdateService. */
public class MyLocationRetrievalState {

	private OriginalCoordinates origin = null;
	private final MyLocationBundle bundle;
	private Exception error = null;
	private boolean staleLocationIndicator = false;
	private boolean isModified = false;
	private boolean isNew = true;
	private Integer id = null;
	
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

	public void setOriginalCoordinates(OriginalCoordinates origin) {
		this.origin = origin;
	}
	
	public boolean hasOriginalCoordinates() {
		return this.origin != null;
	}
	
	public double getOriginalLatitude() {
		if(hasOriginalCoordinates()) {
			return this.origin.getLatitude();
		} else if(hasLocation()) {
			return this.getLocation().getLatitude();
		} else {
			return 0.0;
		}
	}
	
	public double getOriginalLongitude() {
		if(hasOriginalCoordinates()) {
			return this.origin.getLongitude();
		} else if(hasLocation()) {
			return this.getLocation().getLongitude();
		} else {
			return 0.0;
		}
	}
	
	public OriginalCoordinates getOriginalCoordinates() {
		return this.origin;
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
	
	public Integer getLocationId() {
		return this.id;
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
	
	public boolean hasLocationId() {
		return this.id != null;
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
	
	public void setLocationId(Integer id) {
		this.id = id;
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

	public boolean hasAllStaticMaps() {
		return this.bundle.hasAllStaticMaps();
	}

	public boolean hasMissingData() {
		return ! hasLocation() || ! hasAddress() || ! hasAllStaticMaps();
	}

	public void fillFrom(MyLocationBundle originalBundle) {
		bundle.fillFrom(originalBundle);
	}

	public MyLocationBundleRecord toBundleRecord() {
		MyLocationBundleRecord record = new MyLocationBundleRecord();
		record.setBundle(this.bundle);
		if(hasOriginalCoordinates()) {
			record.setOriginalCoordinates(this.origin);
		} else {
			OriginalCoordinates selfCoords = new OriginalCoordinates();
			selfCoords.setLatitude(getOriginalLatitude());
			selfCoords.setLongitude(getOriginalLongitude());
			selfCoords.setId(this.id);
			record.setOriginalCoordinates(selfCoords);
		}
		
		record.setId(this.id);
		
		return record;
	}

	public boolean hasSmallStaticMap() {
		return bundle.hasSmallStaticMap();
	}
	
	public boolean hasMediumStaticMap() {
		return bundle.hasMediumStaticMap();
	}
	
	public boolean hasLargeStaticMap() {
		return bundle.hasLargeStaticMap();
	}

	public String getBasicLongMapsUrl() {
		GoogleMapsLinkBuilder builder = new GoogleMapsLinkBuilder(this.getLocation());
		builder.setCustomMessage("Your friend is here.");
		
		return builder.build();
	}
	
	public String getAddressLongMapsUrl() {
		GoogleMapsLinkBuilder builder = new GoogleMapsLinkBuilder(this.getLocation());
		builder.setCustomMessage("Your friend is at " + getOneLineAddress());
		
		return builder.build();
	}

	public void setShortMapsUrls(ShortMapUrls shortUrl) {
		this.setShortMapsUrls(shortUrl);
	}

	public String getOneLineAddress() {
		String result = null;

		if(bundle.hasAddress()) {
			final Address address = this.bundle.getAddress();
			
			final StringBuilder sb = new StringBuilder();
			final int len = address.getMaxAddressLineIndex();
			
			for(int i = 0; i <= len; i++) {
				if(i > 0) {
					sb.append(" ");
				}
				address.getAddressLine(i);
			}
			result = sb.toString();
		}
		
		return result;
	}

	public String getOneLineCoordinates() {
		final Location location = getLocation();
		return String.format(Locale.ENGLISH, "Lat: %s Long: %s", location.getLatitude(), location.getLongitude());
	}

	public boolean hasBasicShortMapUrl() {
		return bundle.hasBasicShortUrl();
	}
	
	public String getBasicShortMapUrl() {
		return bundle.getShortMapUrls().getBasicShortUrl();
	}

	public boolean hasAddressShortMapUrl() {
		return bundle.hasAddressShortUrl();
	}
	
	public String getAddressShortMapUrl() {
		return bundle.getShortMapUrls().getAddressShortUrl();
	}
	
	public boolean hasShortMapUrls() {
		return bundle.hasShortMapUrls();
	}

	public ShortMapUrls getShortMapUrls() {
		return bundle.getShortMapUrls();
	}

	public boolean hasAllShortMapUrls() {
		return bundle.hasAllShortMapUrls();
	}
}
