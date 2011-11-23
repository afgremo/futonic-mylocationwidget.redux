/*
 ******************************************************************************
 * Parts of this code sample are licensed under Apache License, Version 2.0   *
 * Copyright (c) 2009, Android Open Handset Alliance. All rights reserved.    *
 *																			  *																			*
 * Except as noted, this code sample is offered under a modified BSD license. *
 * Copyright (C) 2010, Motorola Mobility, Inc. All rights reserved.           *
 * 																			  *
 * For more details, see MOTODEV_Studio_for_Android_LicenseNotices.pdf        * 
 * in your installation folder.                                               *
 ******************************************************************************
 */
package com.futonredemption.mylocation.provider;

import java.util.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.text.*;

import com.futonredemption.mylocation.database.*;

public class LocationHistoryContentProvider extends ContentProvider {

	private MyLocationOpenHelper dbHelper;
	private static HashMap<String, String> LOCATIONHISTORY_PROJECTION_MAP;
	private static final String TABLE_NAME = "locationhistory";
	private static final String AUTHORITY = "com.futonredemption.mylocation.provider.locationhistorycontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri _ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase());
	public static final Uri ACCURACY_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/accuracy");
	public static final Uri ALTITUDE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/altitude");
	public static final Uri BEARING_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/bearing");
	public static final Uri LATITUDE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/latitude");
	public static final Uri LONGITUDE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/longitude");
	public static final Uri PROVIDER_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/provider");
	public static final Uri SPEED_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/speed");
	public static final Uri TIME_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/time");

	public static final String DEFAULT_SORT_ORDER = "_id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int LOCATIONHISTORY = 1;
	private static final int LOCATIONHISTORY__ID = 2;
	private static final int LOCATIONHISTORY_ACCURACY = 3;
	private static final int LOCATIONHISTORY_ALTITUDE = 4;
	private static final int LOCATIONHISTORY_BEARING = 5;
	private static final int LOCATIONHISTORY_LATITUDE = 6;
	private static final int LOCATIONHISTORY_LONGITUDE = 7;
	private static final int LOCATIONHISTORY_PROVIDER = 8;
	private static final int LOCATIONHISTORY_SPEED = 9;
	private static final int LOCATIONHISTORY_TIME = 10;

	// Content values keys (using column names)
	public static final String _ID = "_id";
	public static final String ACCURACY = "Accuracy";
	public static final String ALTITUDE = "Altitude";
	public static final String BEARING = "Bearing";
	public static final String LATITUDE = "Latitude";
	public static final String LONGITUDE = "Longitude";
	public static final String PROVIDER = "Provider";
	public static final String SPEED = "Speed";
	public static final String TIME = "Time";

	public boolean onCreate() {
		dbHelper = new MyLocationOpenHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case LOCATIONHISTORY:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(LOCATIONHISTORY_PROJECTION_MAP);
			break;
		case LOCATIONHISTORY__ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("_id=" + url.getPathSegments().get(1));
			break;
		case LOCATIONHISTORY_ACCURACY:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("accuracy='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_ALTITUDE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("altitude='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_BEARING:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("bearing='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_LATITUDE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("latitude='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_LONGITUDE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("longitude='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_PROVIDER:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("provider='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_SPEED:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("speed='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_TIME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("time='" + url.getPathSegments().get(2) + "'");
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		String orderBy = "";
		if (TextUtils.isEmpty(sort)) {
			orderBy = DEFAULT_SORT_ORDER;
		} else {
			orderBy = sort;
		}
		Cursor c = qb.query(mDB, projection, selection, selectionArgs, null,
				null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), url);
		return c;
	}

	public String getType(Uri url) {
		switch (URL_MATCHER.match(url)) {
		case LOCATIONHISTORY:
			return "vnd.android.cursor.dir/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY__ID:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_ACCURACY:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_ALTITUDE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_BEARING:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_LATITUDE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_LONGITUDE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_PROVIDER:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_SPEED:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_TIME:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
	}

	public Uri insert(Uri url, ContentValues initialValues) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		long rowID;
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		if (URL_MATCHER.match(url) != LOCATIONHISTORY) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("locationhistory", "locationhistory", values);
		if (rowID > 0) {
			Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		throw new SQLException("Failed to insert row into " + url);
	}

	public int delete(Uri url, String where, String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case LOCATIONHISTORY:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case LOCATIONHISTORY__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.delete(TABLE_NAME,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ACCURACY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"accuracy="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ALTITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"altitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_BEARING:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"bearing="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_LATITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"latitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_LONGITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"longitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_PROVIDER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"provider="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_SPEED:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"speed="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_TIME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"time="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	public int update(Uri url, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase mDB = dbHelper.getWritableDatabase();
		int count;
		String segment = "";
		switch (URL_MATCHER.match(url)) {
		case LOCATIONHISTORY:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case LOCATIONHISTORY__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.update(TABLE_NAME, values,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ACCURACY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"accuracy="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ALTITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"altitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_BEARING:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"bearing="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_LATITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"latitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_LONGITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"longitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_PROVIDER:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"provider="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_SPEED:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"speed="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_TIME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"time="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URL " + url);
		}
		getContext().getContentResolver().notifyChange(url, null);
		return count;
	}

	static {
		URL_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URL_MATCHER
				.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), LOCATIONHISTORY);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/#",
				LOCATIONHISTORY__ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/accuracy"
				+ "/*", LOCATIONHISTORY_ACCURACY);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/altitude"
				+ "/*", LOCATIONHISTORY_ALTITUDE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/bearing"
				+ "/*", LOCATIONHISTORY_BEARING);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/latitude"
				+ "/*", LOCATIONHISTORY_LATITUDE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/longitude"
				+ "/*", LOCATIONHISTORY_LONGITUDE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/provider"
				+ "/*", LOCATIONHISTORY_PROVIDER);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/speed"
				+ "/*", LOCATIONHISTORY_SPEED);
		URL_MATCHER
				.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/time" + "/*",
						LOCATIONHISTORY_TIME);

		LOCATIONHISTORY_PROJECTION_MAP = new HashMap<String, String>();
		LOCATIONHISTORY_PROJECTION_MAP.put(_ID, "_id");
		LOCATIONHISTORY_PROJECTION_MAP.put(ACCURACY, "accuracy");
		LOCATIONHISTORY_PROJECTION_MAP.put(ALTITUDE, "altitude");
		LOCATIONHISTORY_PROJECTION_MAP.put(BEARING, "bearing");
		LOCATIONHISTORY_PROJECTION_MAP.put(LATITUDE, "latitude");
		LOCATIONHISTORY_PROJECTION_MAP.put(LONGITUDE, "longitude");
		LOCATIONHISTORY_PROJECTION_MAP.put(PROVIDER, "provider");
		LOCATIONHISTORY_PROJECTION_MAP.put(SPEED, "speed");
		LOCATIONHISTORY_PROJECTION_MAP.put(TIME, "time");

	}
}
