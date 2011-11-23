package com.futonredemption.mylocation.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Location;
import android.net.Uri;

import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.provider.AddressContentProvider;
import com.futonredemption.mylocation.provider.LocationHistoryContentProvider;

public class MyLocationBundlePersistence {

	private final Context context;
	public MyLocationBundlePersistence(final Context context) {
		this.context = context;
	}
	
	public MyLocationBundle get(int id) {
		MyLocationBundle bundle = new MyLocationBundle();
		final Location location = getLocation(id);
		bundle.setLocation(location);
		return bundle;
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
		final Uri contentUri = ContentUris.withAppendedId(LocationHistoryContentProvider.CONTENT_URI, id);
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
	
	
}
