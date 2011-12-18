package com.futonredemption.mylocation.persistence;

import java.util.Locale;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Address;
import android.location.Location;

import com.futonredemption.mylocation.OriginalCoordinates;
import com.futonredemption.mylocation.ShortMapUrls;
import com.futonredemption.mylocation.StaticMap;
import com.futonredemption.mylocation.provider.LocationHistoryContentProvider;

public class CursorConverters {

	public static void appendValues(final ContentValues values, StaticMap staticMap) {
		values.put(LocationHistoryContentProvider.MAPTILEURLSMALL, staticMap.getSmallMapUrl());
		values.put(LocationHistoryContentProvider.MAPTILEFILESMALL, staticMap.getSmallMapFilePath());
		values.put(LocationHistoryContentProvider.MAPTILEURLMEDIUM, staticMap.getMediumMapUrl());
		values.put(LocationHistoryContentProvider.MAPTILEFILEMEDIUM, staticMap.getMediumMapFilePath());
		values.put(LocationHistoryContentProvider.MAPTILEURLLARGE, staticMap.getLargeMapUrl());
		values.put(LocationHistoryContentProvider.MAPTILEFILELARGE, staticMap.getLargeMapFilePath());
	}
	
	public static StaticMap toStaticMap(Cursor cursor) {
		
		StaticMap map = new StaticMap();
		String filePath;
		String url;
		
		url = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.MAPTILEURLSMALL));
		filePath = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.MAPTILEFILESMALL));
		map.setSmallMapUrl(url);
		map.setSmallMapFilePath(filePath);
		
		url = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.MAPTILEURLMEDIUM));
		filePath = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.MAPTILEFILEMEDIUM));
		map.setMediumMapUrl(url);
		map.setMediumMapFilePath(filePath);
		
		url = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.MAPTILEURLLARGE));
		filePath = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.MAPTILEFILELARGE));
		map.setLargeMapUrl(url);
		map.setLargeMapFilePath(filePath);
		
		return map;
	}
	
	public static void appendValues(final ContentValues values, ShortMapUrls shortUrls) {
		values.put(LocationHistoryContentProvider.BASICSHORTURL, shortUrls.getBasicShortUrl());
		values.put(LocationHistoryContentProvider.ADDRESSSHORTURL, shortUrls.getAddressShortUrl());
	}
	
	public static ShortMapUrls toShortMapUrls(Cursor cursor) {
		
		ShortMapUrls shortUrls = new ShortMapUrls();
		String basicUrl;
		String addressUrl;
		
		basicUrl = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.BASICSHORTURL));
		addressUrl = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.ADDRESSLINES));
		shortUrls.setBasicShortUrl(basicUrl);
		shortUrls.setAddressShortUrl(addressUrl);
		
		return shortUrls;
	}

	public static void appendValues(final ContentValues values, final Address address) {
		PackedString addressFlat = new PackedString();
		final int len = address.getMaxAddressLineIndex();
		for(int i = 0; i <= len; i++) {
			addressFlat.put(address.getAddressLine(i));
		}
		
		values.put(LocationHistoryContentProvider.ADDRESSLINES, addressFlat.toString());
		values.put(LocationHistoryContentProvider.ADMINAREA, address.getAdminArea());
		values.put(LocationHistoryContentProvider.COUNTRYCODE, address.getCountryCode());
		values.put(LocationHistoryContentProvider.COUNTRYNAME, address.getCountryName());
		values.put(LocationHistoryContentProvider.FEATURENAME, address.getFeatureName());
		// Duplicated by location, not needed.
		//values.put(LocationHistoryContentProvider.LATITUDE, address.getLatitude());
		//values.put(LocationHistoryContentProvider.LONGITUDE, address.getLongitude());
		values.put(LocationHistoryContentProvider.LOCALE, Converters.toString(address.getLocale()));
		values.put(LocationHistoryContentProvider.LOCALITY, address.getLocality());
		values.put(LocationHistoryContentProvider.PHONE, address.getPhone());
		values.put(LocationHistoryContentProvider.POSTALCODE, address.getPostalCode());
		values.put(LocationHistoryContentProvider.PREMISES, address.getPremises());
		values.put(LocationHistoryContentProvider.SUBADMINAREA, address.getSubAdminArea());
		values.put(LocationHistoryContentProvider.SUBLOCALITY, address.getSubLocality());
		values.put(LocationHistoryContentProvider.SUBTHOROUGHFARE, address.getSubThoroughfare());
		values.put(LocationHistoryContentProvider.THOROUGHFARE, address.getThoroughfare());
		values.put(LocationHistoryContentProvider.ADDRESSURL, address.getUrl());
	}
	
	public static Address toAddress(Cursor cursor) {
		String addressLines = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.ADDRESSLINES));
		String adminArea = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.ADMINAREA));
		String countryCode = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.COUNTRYCODE));
		String countryName = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.COUNTRYNAME));
		String featureName = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.FEATURENAME));
		double latitude = cursor.getDouble(cursor.getColumnIndex(LocationHistoryContentProvider.LATITUDE));
		double longitude = cursor.getDouble(cursor.getColumnIndex(LocationHistoryContentProvider.LONGITUDE));
		String locale = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.LOCALE));
		String locality = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.LOCALITY));
		String phone = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.PHONE));
		String postalCode = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.POSTALCODE));
		String premises = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.PREMISES));
		String subAdminArea = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.SUBADMINAREA));
		String sublocality = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.SUBLOCALITY));
		String subthoroughfare = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.SUBTHOROUGHFARE));
		String thoroughfare = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.THOROUGHFARE));
		String url = cursor.getString(cursor.getColumnIndex(LocationHistoryContentProvider.ADDRESSURL));
		
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

	public static void appendValues(final ContentValues values, final Location location) {
		values.put(LocationHistoryContentProvider.ACCURACY, location.getAccuracy());
		values.put(LocationHistoryContentProvider.ALTITUDE, location.getAltitude());
		values.put(LocationHistoryContentProvider.BEARING, location.getBearing());
		values.put(LocationHistoryContentProvider.LATITUDE, location.getLatitude());
		values.put(LocationHistoryContentProvider.LONGITUDE, location.getLongitude());
		values.put(LocationHistoryContentProvider.PROVIDER, location.getProvider());
		values.put(LocationHistoryContentProvider.SPEED, location.getSpeed());
		values.put(LocationHistoryContentProvider.TIME, location.getTime());
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

	public static void appendValues(final ContentValues values, final OriginalCoordinates coords) {
		values.put(LocationHistoryContentProvider.ORIGINALLATITUDE, coords.getLatitude());
		values.put(LocationHistoryContentProvider.ORIGINALLONGITUDE, coords.getLongitude());
	}
	
	public static OriginalCoordinates toOriginalCoordinates(final Cursor cursor) {
		final OriginalCoordinates coordinates = new OriginalCoordinates();
		double latitude = cursor.getFloat(cursor.getColumnIndex(LocationHistoryContentProvider.ORIGINALLATITUDE));
		double longitude = cursor.getDouble(cursor.getColumnIndex(LocationHistoryContentProvider.ORIGINALLONGITUDE));
		
		coordinates.setLatitude(latitude);
		coordinates.setLongitude(longitude);
		return coordinates;
	}
}
