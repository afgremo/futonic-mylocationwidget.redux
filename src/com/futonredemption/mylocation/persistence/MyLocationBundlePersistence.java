package com.futonredemption.mylocation.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Location;
import android.net.Uri;

import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.StaticMap;
import com.futonredemption.mylocation.provider.AddressContentProvider;
import com.futonredemption.mylocation.provider.LocationHistoryContentProvider;
import com.futonredemption.mylocation.provider.StaticMapContentProvider;

public class MyLocationBundlePersistence {

	private final Context context;
	public MyLocationBundlePersistence(final Context context) {
		this.context = context;
	}
	
	public long save(MyLocationBundle bundle) {
		final ContentResolver resolver = context.getContentResolver();
		ContentValues values;
		long locationSID = -1;
		
		if(bundle.hasLocation()) {
			final Location location = bundle.getLocation();
			values = CursorConverters.toContentValues(location);
			Uri locationUri = resolver.insert(LocationHistoryContentProvider.CONTENT_URI, values);

			locationSID = ContentUris.parseId(locationUri);
			
			if(bundle.hasAddress()) {
				final Address address = bundle.getAddress();
				values = CursorConverters.toContentValues(locationSID, address);
				resolver.insert(AddressContentProvider.CONTENT_URI, values);
			}
			
			if(bundle.hasStaticMap()) {
				final StaticMap staticMap = bundle.getStaticMap();
				values = CursorConverters.toContentValues(locationSID, staticMap);
				resolver.insert(StaticMapContentProvider.CONTENT_URI, values);
			}
		}
		
		return locationSID;
	}
	
	private static String [] LocationHistoryContentProviderIdOnlyProjection = new String [] { LocationHistoryContentProvider._ID };
	public MyLocationBundle getMostRecent() {
		int locationId = 0;
		final ContentResolver resolver = context.getContentResolver();
		Cursor cursor = null;
		
		try {
			cursor = resolver.query(LocationHistoryContentProvider.CONTENT_URI, LocationHistoryContentProviderIdOnlyProjection, 
					null, null, 
					LocationHistoryContentProvider._ID + " DESC LIMIT 1");
			if(cursor.moveToFirst()) {
				locationId = cursor.getInt(0);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}

		return get(locationId);
	}
	
	public MyLocationBundle get(int locationId) {
		MyLocationBundle bundle = new MyLocationBundle();
		final Location location = getLocationFromLocationId(locationId);
		final Address address = getAddressFromLocationId(locationId);
		final StaticMap staticMap = getStaticMapFromLocationId(locationId);
		bundle.setLocation(location);
		bundle.setAddress(address);
		bundle.setStaticMap(staticMap);
		return bundle;
	}
	
	public Location getLocationFromLocationId(int id) {
		return getLocation(id);
	}
	
	public Location getLocation(int id) {
		Location location = null;
		final ContentResolver resolver = context.getContentResolver();
		final Uri contentUri = ContentUris.withAppendedId(LocationHistoryContentProvider.CONTENT_URI, id);
		Cursor cursor = null;
		
		try {
			cursor = resolver.query(contentUri, null, null, null, LocationHistoryContentProvider.DEFAULT_SORT_ORDER);
			if(cursor.moveToFirst()) {
				location = CursorConverters.toLocation(cursor);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		
		return location;
	}
	
	public Address getAddress(int id) {
		Address address = null;
		final ContentResolver resolver = context.getContentResolver();
		final Uri contentUri = ContentUris.withAppendedId(AddressContentProvider.CONTENT_URI, id);
		Cursor cursor = null;
		
		try {
			cursor = resolver.query(contentUri, null, null, null, AddressContentProvider.DEFAULT_SORT_ORDER);
			if(cursor.moveToFirst()) {
				address = CursorConverters.toAddress(cursor);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		
		return address;
	}
	
	public Address getAddressFromLocationId(int id) {
		Address address = null;
		final ContentResolver resolver = context.getContentResolver();
		final Uri contentUri = ContentUris.withAppendedId(AddressContentProvider.LOCATIONSID_FIELD_CONTENT_URI, id);
		Cursor cursor = null;
		
		try {
			cursor = resolver.query(contentUri, null, null, null, AddressContentProvider.DEFAULT_SORT_ORDER);
			if(cursor.moveToFirst()) {
				address = CursorConverters.toAddress(cursor);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		
		return address;
	}
	
	public StaticMap getStaticMap(int id) {
		StaticMap staticMap = null;
		final ContentResolver resolver = context.getContentResolver();
		final Uri contentUri = ContentUris.withAppendedId(StaticMapContentProvider.CONTENT_URI, id);
		Cursor cursor = null;
		
		try {
			cursor = resolver.query(contentUri, null, null, null, StaticMapContentProvider.DEFAULT_SORT_ORDER);
			if(cursor.moveToFirst()) {
				staticMap = CursorConverters.toStaticMap(cursor);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		
		return staticMap;
	}
	public StaticMap getStaticMapFromLocationId(int id) {
		StaticMap staticMap = null;
		final ContentResolver resolver = context.getContentResolver();
		final Uri contentUri = ContentUris.withAppendedId(StaticMapContentProvider.LOCATIONSID_FIELD_CONTENT_URI, id);
		Cursor cursor = null;
		
		try {
			cursor = resolver.query(contentUri, null, null, null, StaticMapContentProvider.DEFAULT_SORT_ORDER);
			if(cursor.moveToFirst()) {
				staticMap = CursorConverters.toStaticMap(cursor);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		
		return staticMap;
	}
}
