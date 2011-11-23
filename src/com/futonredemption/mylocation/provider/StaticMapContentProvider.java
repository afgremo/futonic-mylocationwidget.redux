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

public class StaticMapContentProvider extends ContentProvider {

	private MyLocationOpenHelper dbHelper;
	private static HashMap<String, String> STATICMAP_PROJECTION_MAP;
	private static final String TABLE_NAME = "staticmap";
	private static final String AUTHORITY = "com.futonredemption.mylocation.provider.staticmapcontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri _ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase());
	public static final Uri URL_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/url");
	public static final Uri LOCATIONSID_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/locationsid");
	public static final Uri FILEPATH_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/filepath");

	public static final String DEFAULT_SORT_ORDER = "_id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int STATICMAP = 1;
	private static final int STATICMAP__ID = 2;
	private static final int STATICMAP_URL = 3;
	private static final int STATICMAP_LOCATIONSID = 4;
	private static final int STATICMAP_FILEPATH = 5;

	// Content values keys (using column names)
	public static final String _ID = "_id";
	public static final String URL = "Url";
	public static final String LOCATIONSID = "LocationSID";
	public static final String FILEPATH = "FilePath";

	public boolean onCreate() {
		dbHelper = new MyLocationOpenHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case STATICMAP:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(STATICMAP_PROJECTION_MAP);
			break;
		case STATICMAP__ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("_id=" + url.getPathSegments().get(1));
			break;
		case STATICMAP_URL:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("url='" + url.getPathSegments().get(2) + "'");
			break;
		case STATICMAP_LOCATIONSID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("locationsid='" + url.getPathSegments().get(2) + "'");
			break;
		case STATICMAP_FILEPATH:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("filepath='" + url.getPathSegments().get(2) + "'");
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
		case STATICMAP:
			return "vnd.android.cursor.dir/vnd.com.futonredemption.mylocation.provider.staticmap";
		case STATICMAP__ID:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.staticmap";
		case STATICMAP_URL:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.staticmap";
		case STATICMAP_LOCATIONSID:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.staticmap";
		case STATICMAP_FILEPATH:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.staticmap";

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
		if (URL_MATCHER.match(url) != STATICMAP) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("staticmap", "staticmap", values);
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
		case STATICMAP:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case STATICMAP__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.delete(TABLE_NAME,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case STATICMAP_URL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"url="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case STATICMAP_LOCATIONSID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"locationsid="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case STATICMAP_FILEPATH:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"filepath="
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
		case STATICMAP:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case STATICMAP__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.update(TABLE_NAME, values,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case STATICMAP_URL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"url="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case STATICMAP_LOCATIONSID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"locationsid="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case STATICMAP_FILEPATH:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"filepath="
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
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), STATICMAP);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/#",
				STATICMAP__ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/url" + "/*",
				STATICMAP_URL);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/locationsid"
				+ "/*", STATICMAP_LOCATIONSID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/filepath"
				+ "/*", STATICMAP_FILEPATH);

		STATICMAP_PROJECTION_MAP = new HashMap<String, String>();
		STATICMAP_PROJECTION_MAP.put(_ID, "_id");
		STATICMAP_PROJECTION_MAP.put(URL, "url");
		STATICMAP_PROJECTION_MAP.put(LOCATIONSID, "locationsid");
		STATICMAP_PROJECTION_MAP.put(FILEPATH, "filepath");

	}
}
