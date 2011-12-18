package com.futonredemption.mylocation.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Location;
import android.net.Uri;

import com.futonredemption.mylocation.Constants;
import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.OriginalCoordinates;
import com.futonredemption.mylocation.ShortMapUrls;
import com.futonredemption.mylocation.StaticMap;
import com.futonredemption.mylocation.provider.LocationHistoryContentProvider;

public class MyLocationBundlePersistence {

	private static final double RANGE_SIZE = 0.001;
	private static final String [] OriginalLatLongProjection = new String [] { LocationHistoryContentProvider._ID, LocationHistoryContentProvider.ORIGINALLATITUDE, LocationHistoryContentProvider.ORIGINALLONGITUDE };
	private static final String [] IdOnlyProjection = new String [] { LocationHistoryContentProvider._ID };
	private static final String RoughCloseEnoughQueryString;
	
	static {
		final StringBuilder sb = new StringBuilder();
		sb.append(LocationHistoryContentProvider.ORIGINALLATITUDE);
		sb.append(" >= ? and ");
		sb.append(LocationHistoryContentProvider.ORIGINALLATITUDE);
		sb.append(" <= ? and ");
		
		sb.append(LocationHistoryContentProvider.ORIGINALLONGITUDE);
		sb.append(" >= ? and ");
		sb.append(LocationHistoryContentProvider.ORIGINALLONGITUDE);
		sb.append(" <= ?");
		
		RoughCloseEnoughQueryString = sb.toString();
	}
	
	private final Context context;
	public MyLocationBundlePersistence(final Context context) {
		this.context = context;
	}
	
	private void valuesForInsert(final ContentValues values, MyLocationBundleRecord record) {
		final MyLocationBundle bundle = record.getBundle();
		final OriginalCoordinates coords = record.getOriginalCoordinates();
		final Location location = bundle.getLocation();
		
		CursorConverters.appendValues(values, location);
		
		if(bundle.hasAddress()) {
			final Address address = bundle.getAddress();
			CursorConverters.appendValues(values, address);
		}
		
		if(bundle.hasStaticMap()) {
			final StaticMap staticMap = bundle.getStaticMap();
			CursorConverters.appendValues(values, staticMap);
		}
		
		if(bundle.hasShortMapUrls()) {
			final ShortMapUrls mapLinks = bundle.getShortMapUrls();
			CursorConverters.appendValues(values, mapLinks);
		}
		CursorConverters.appendValues(values, coords);
	}
	
	public boolean update(MyLocationBundleRecord record) {
		final ContentResolver resolver = context.getContentResolver();
		final ContentValues values = new ContentValues();
		
		if(record.getBundle().hasLocation()) {
			valuesForInsert(values, record);
			
			final Uri contentUri = ContentUris.withAppendedId(LocationHistoryContentProvider.CONTENT_URI, record.getId());
			
			resolver.update(contentUri, values, null, null);
		}
		
		return true;
	}
	
	public long insert(MyLocationBundleRecord record) {
		final ContentResolver resolver = context.getContentResolver();
		final ContentValues values = new ContentValues();
		long locationSID = -1;
		
		if(record.getBundle().hasLocation()) {
			valuesForInsert(values, record);
			
			final Uri savedUri = resolver.insert(LocationHistoryContentProvider.CONTENT_URI, values);
			locationSID = ContentUris.parseId(savedUri);
		}
		
		return locationSID;
	}
	
	public Integer getMostRecentId() {
		Integer result = null;
		final ContentResolver resolver = context.getContentResolver();
		Cursor cursor = null;
		
		try {
			cursor = resolver.query(LocationHistoryContentProvider.CONTENT_URI, IdOnlyProjection, 
					null, null, 
					LocationHistoryContentProvider._ID + " DESC LIMIT 1");
			if(cursor.moveToFirst()) {
				result = new Integer(cursor.getInt(0));
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}

		return result;
	}
	
	public OriginalCoordinates getCloseEnough(double latitude, double longitude) {
		OriginalCoordinates coordDesc = null;
		final ContentResolver resolver = context.getContentResolver();
		Cursor cursor = null;
		final Location currentLoc = new Location("");
		final Location storedLoc = new Location("");
		currentLoc.setLatitude(latitude);
		currentLoc.setLongitude(longitude);

		// Looks ugly, Used for > and < criteria.
		final String [] queryParams = new String[] {
					Double.toString(latitude - RANGE_SIZE), Double.toString(latitude + RANGE_SIZE), 
					Double.toString(longitude - RANGE_SIZE), Double.toString(longitude + RANGE_SIZE)
		};
		
		try {
			cursor = resolver.query(LocationHistoryContentProvider.CONTENT_URI, OriginalLatLongProjection, 
					RoughCloseEnoughQueryString, queryParams,
					LocationHistoryContentProvider._ID + " DESC");
			
			if(cursor.moveToFirst()) {
				do {
					storedLoc.setLatitude(cursor.getDouble(1));
					storedLoc.setLongitude(cursor.getDouble(2));
					if(currentLoc.distanceTo(storedLoc) <= Constants.CloseEnoughDistanceInMeters) {
						coordDesc = new OriginalCoordinates();
						coordDesc.setId(cursor.getInt(0));
						coordDesc.setLatitude(storedLoc.getLatitude());
						coordDesc.setLongitude(storedLoc.getLongitude());
					}
				} while(cursor.moveToNext() && coordDesc == null);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}

		return coordDesc;
	}
	
	public MyLocationBundleRecord get(int id) {
		final MyLocationBundleRecord record = new MyLocationBundleRecord();
		MyLocationBundle bundle = null;
		Location location = null;
		Address address = null;
		StaticMap staticMap;
		ShortMapUrls shortUrls;
		OriginalCoordinates coordinates = null;
		final ContentResolver resolver = context.getContentResolver();
		final Uri contentUri = ContentUris.withAppendedId(LocationHistoryContentProvider.CONTENT_URI, id);
		Cursor cursor = null;
		
		try {
			cursor = resolver.query(contentUri, null, null, null, LocationHistoryContentProvider.DEFAULT_SORT_ORDER);
			if(cursor.moveToFirst()) {
				bundle = new MyLocationBundle();
				location = CursorConverters.toLocation(cursor);
				try {
					address = CursorConverters.toAddress(cursor);
				} catch(Exception e) {
					Debugging.e(e);
					address = null;
				}
				try {
					staticMap = CursorConverters.toStaticMap(cursor);
				} catch(Exception e) {
					Debugging.e(e);
					staticMap = null;
				}
				try {
					shortUrls = CursorConverters.toShortMapUrls(cursor);
				} catch(Exception e) {
					Debugging.e(e);
					shortUrls = null;
				}
				try {
					coordinates = CursorConverters.toOriginalCoordinates(cursor);
				} catch(Exception e) {
					Debugging.e(e);
					coordinates = null;
				}
				bundle.setLocation(location);
				bundle.setAddress(address);
				bundle.setStaticMap(staticMap);
				bundle.setShortMapUrls(shortUrls);
				record.setBundle(bundle);
				record.setOriginalCoordinates(coordinates);
				record.setId(cursor.getInt(cursor.getColumnIndex(LocationHistoryContentProvider._ID)));
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		
		return record;
	}
}
