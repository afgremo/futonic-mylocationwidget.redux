package com.futonredemption.mylocation.persistence;

import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Address;
import android.location.Location;

import com.futonredemption.mylocation.provider.AddressContentProvider;
import com.futonredemption.mylocation.provider.LocationHistoryContentProvider;

public class CursorConverters {

	public static ConventValues toContentValues(int locationSID, Address address) {
		final ContentValues values = new ContentValues();
		
		PackedString addressFlat = new PackedString();
		final int len = address.getMaxAddressLineIndex();
		for(int i = 0; i < len; i++) {
			addressFlat.put(address.getAddressLine(i));
		}
		
		values.put(AddressContentProvider.LOCATIONSID, locationSID);
		values.put(AddressContentProvider.ADDRESSLINES, addressFlat.toString());
		values.put(AddressContentProvider.ADMINAREA, address.getAdminArea());
		values.put(AddressContentProvider.COUNTRYCODE, address.getCountryCode());
		values.put(AddressContentProvider.COUNTRYNAME, address.getCountryName());
		values.put(AddressContentProvider.FEATURENAME, address.getFeatureName());
		values.put(AddressContentProvider.LATITUDE, address.getLatitude());
		values.put(AddressContentProvider.LONGITUDE, address.getLongitude());
		values.put(AddressContentProvider.LOCALE, Converters.toString(address.getLocale()));
		values.put(AddressContentProvider.LOCALITY, address.getLocality());
		values.put(AddressContentProvider.PHONE, address.getPhone());
		values.put(AddressContentProvider.POSTALCODE, address.getPostalCode());
		values.put(AddressContentProvider.PREMISES, address.getPremises());
		values.put(AddressContentProvider.SUBADMINAREA, address.getSubAdminArea());
		values.put(AddressContentProvider.SUBLOCALITY, address.getSubLocality());
		values.put(AddressContentProvider.SUBTHOROUGHFARE, address.getSubThoroughfare());
		values.put(AddressContentProvider.THOROUGHFARE, address.getThoroughfare());
		values.put(AddressContentProvider.URL, address.getUrl());

		return values;
	}
	
	public static Address toAddress(Cursor cursor) {
		String addressLines = cursor.getString(cursor.getColumnIndex(AddressContentProvider.ADDRESSLINES));
		String adminArea = cursor.getString(cursor.getColumnIndex(AddressContentProvider.ADMINAREA));
		String countryCode = cursor.getString(cursor.getColumnIndex(AddressContentProvider.COUNTRYCODE));
		String countryName = cursor.getString(cursor.getColumnIndex(AddressContentProvider.COUNTRYNAME));
		String featureName = cursor.getString(cursor.getColumnIndex(AddressContentProvider.FEATURENAME));
		double latitude = cursor.getDouble(cursor.getColumnIndex(AddressContentProvider.LATITUDE));
		double longitude = cursor.getDouble(cursor.getColumnIndex(AddressContentProvider.LONGITUDE));
		String locale = cursor.getString(cursor.getColumnIndex(AddressContentProvider.LOCALE));
		String locality = cursor.getString(cursor.getColumnIndex(AddressContentProvider.LOCALITY));
		String phone = cursor.getString(cursor.getColumnIndex(AddressContentProvider.PHONE));
		String postalCode = cursor.getString(cursor.getColumnIndex(AddressContentProvider.POSTALCODE));
		String premises = cursor.getString(cursor.getColumnIndex(AddressContentProvider.PREMISES));
		String subAdminArea = cursor.getString(cursor.getColumnIndex(AddressContentProvider.SUBADMINAREA));
		String sublocality = cursor.getString(cursor.getColumnIndex(AddressContentProvider.SUBLOCALITY));
		String subthoroughfare = cursor.getString(cursor.getColumnIndex(AddressContentProvider.SUBTHOROUGHFARE));
		String thoroughfare = cursor.getString(cursor.getColumnIndex(AddressContentProvider.THOROUGHFARE));
		String url = cursor.getString(cursor.getColumnIndex(AddressContentProvider.URL));
		
		final Locale localeNative = Converters.fromString(locale);
		final Address address = new Address(localeNative);
		address.setAdminArea(adminArea);
		address.setCountryCode(countryCode);
		address.setCountryName(countryName);
		address.setFeatureName(featureName);
		address.setLatitude(latitude);
		address.setLongitude(longitude);
		address.setLocality(locality);
		address.setPhone(phone);
		address.setPostalCode(postalCode);
		address.setPremises(premises);
		address.setSubAdminArea(subAdminArea);
		address.setSubLocality(sublocality);
		address.setSubThoroughfare(subthoroughfare);
		address.setThoroughfare(thoroughfare);
		address.setUrl(url);
		
		String [] addressLinesUnpacked = PackedString.unpack(addressLines);
		int len = addressLinesUnpacked.length;
		
		for(int i = 0; i < len; i++) {
			address.setAddressLine(i, addressLinesUnpacked[i]);
		}
		
		return address;
	}

	public static ContentValues toContentValues(Location location) {
		ContentValues values = new ContentValues();
		
		values.put(LocationHistoryContentProvider.ACCURACY, location.getAccuracy());
		values.put(LocationHistoryContentProvider.ALTITUDE, location.getAltitude());
		values.put(LocationHistoryContentProvider.BEARING, location.getBearing());
		values.put(LocationHistoryContentProvider.LATITUDE, location.getLatitude());
		values.put(LocationHistoryContentProvider.LONGITUDE, location.getLongitude());
		values.put(LocationHistoryContentProvider.PROVIDER, location.getProvider());
		values.put(LocationHistoryContentProvider.SPEED, location.getSpeed());
		values.put(LocationHistoryContentProvider.TIME, location.getTime());
		return values;
	}
	
	public static Location toLocation(Cursor cursor) {
		
		float accuracy = cursor.getFloat(cursor.getColumnIndex(LocationHistoryContentProvider.ACCURACY));
		double altitude = cursor.getDouble(cursor.getColumnIndex(LocationHistoryContentProvider.ALTITUDE));
		float bearing = cursor.getFloat(cursor.getColumnIndex(LocationHistoryContentProvider.BEARING));
		double latitude = cursor.getDouble(cursor.getColumnIndex(LocationHistoryContentProvider.LATITUDE));
		double longitude = cursor.getDouble(cursor.getColumnIndex(LocationHistoryContentProvider.LONGITUDE));
		String provider = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.PROVIDER));
		float speed = cursor.getFloat(cursor.getColumnIndex(LocationHistoryContentProvider.SPEED));
		long time = cursor.getInt(cursor.getColumnIndex(LocationHistoryContentProvider.TIME));
		
		Location location = new Location(provider);
		if(accuracy != -1F)
			location.setAccuracy(accuracy);
		if(altitude != -1000000F)
			location.setAltitude(altitude);
		if(bearing != -1F)
			location.setBearing(bearing);
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		location.setSpeed(speed);
		location.setTime(time);

		return location;
	}
}
