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
	public static final Uri ADDRESSLINES_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/addresslines");
	public static final Uri ADMINAREA_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/adminarea");
	public static final Uri COUNTRYCODE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/countrycode");
	public static final Uri COUNTRYNAME_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/countryname");
	public static final Uri FEATURENAME_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/featurename");
	public static final Uri LOCALE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/locale");
	public static final Uri LOCALITY_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/locality");
	public static final Uri PHONE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/phone");
	public static final Uri POSTALCODE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/postalcode");
	public static final Uri PREMISES_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/premises");
	public static final Uri SUBADMINAREA_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/subadminarea");
	public static final Uri SUBLOCALITY_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/sublocality");
	public static final Uri SUBTHOROUGHFARE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/subthoroughfare");
	public static final Uri THOROUGHFARE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/thoroughfare");
	public static final Uri ADDRESSURL_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/addressurl");
	public static final Uri MAPTILEURLSMALL_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/maptileurlsmall");
	public static final Uri MAPTILEFILESMALL_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/maptilefilesmall");
	public static final Uri MAPTILEURLMEDIUM_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/maptileurlmedium");
	public static final Uri MAPTILEFILEMEDIUM_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/maptilefilemedium");
	public static final Uri MAPTILEURLLARGE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/maptileurllarge");
	public static final Uri MAPTILEFILELARGE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/maptilefilelarge");
	public static final Uri ORIGINALLATITUDE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/originallatitude");
	public static final Uri ORIGINALLONGITUDE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/originallongitude");
	public static final Uri ORIGINALID_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/originalid");

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
	private static final int LOCATIONHISTORY_ADDRESSLINES = 11;
	private static final int LOCATIONHISTORY_ADMINAREA = 12;
	private static final int LOCATIONHISTORY_COUNTRYCODE = 13;
	private static final int LOCATIONHISTORY_COUNTRYNAME = 14;
	private static final int LOCATIONHISTORY_FEATURENAME = 15;
	private static final int LOCATIONHISTORY_LOCALE = 16;
	private static final int LOCATIONHISTORY_LOCALITY = 17;
	private static final int LOCATIONHISTORY_PHONE = 18;
	private static final int LOCATIONHISTORY_POSTALCODE = 19;
	private static final int LOCATIONHISTORY_PREMISES = 20;
	private static final int LOCATIONHISTORY_SUBADMINAREA = 21;
	private static final int LOCATIONHISTORY_SUBLOCALITY = 22;
	private static final int LOCATIONHISTORY_SUBTHOROUGHFARE = 23;
	private static final int LOCATIONHISTORY_THOROUGHFARE = 24;
	private static final int LOCATIONHISTORY_ADDRESSURL = 25;
	private static final int LOCATIONHISTORY_MAPTILEURLSMALL = 26;
	private static final int LOCATIONHISTORY_MAPTILEFILESMALL = 27;
	private static final int LOCATIONHISTORY_MAPTILEURLMEDIUM = 28;
	private static final int LOCATIONHISTORY_MAPTILEFILEMEDIUM = 29;
	private static final int LOCATIONHISTORY_MAPTILEURLLARGE = 30;
	private static final int LOCATIONHISTORY_MAPTILEFILELARGE = 31;
	private static final int LOCATIONHISTORY_ORIGINALLATITUDE = 32;
	private static final int LOCATIONHISTORY_ORIGINALLONGITUDE = 33;
	private static final int LOCATIONHISTORY_ORIGINALID = 34;

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
	public static final String ADDRESSLINES = "AddressLines";
	public static final String ADMINAREA = "AdminArea";
	public static final String COUNTRYCODE = "CountryCode";
	public static final String COUNTRYNAME = "CountryName";
	public static final String FEATURENAME = "FeatureName";
	public static final String LOCALE = "Locale";
	public static final String LOCALITY = "Locality";
	public static final String PHONE = "Phone";
	public static final String POSTALCODE = "PostalCode";
	public static final String PREMISES = "Premises";
	public static final String SUBADMINAREA = "SubAdminArea";
	public static final String SUBLOCALITY = "SubLocality";
	public static final String SUBTHOROUGHFARE = "SubThoroughfare";
	public static final String THOROUGHFARE = "Thoroughfare";
	public static final String ADDRESSURL = "AddressUrl";
	public static final String MAPTILEURLSMALL = "MapTileUrlSmall";
	public static final String MAPTILEFILESMALL = "MapTileFileSmall";
	public static final String MAPTILEURLMEDIUM = "MapTileUrlMedium";
	public static final String MAPTILEFILEMEDIUM = "MapTileFileMedium";
	public static final String MAPTILEURLLARGE = "MapTileUrlLarge";
	public static final String MAPTILEFILELARGE = "MapTileFileLarge";
	public static final String ORIGINALLATITUDE = "OriginalLatitude";
	public static final String ORIGINALLONGITUDE = "OriginalLongitude";
	public static final String ORIGINALID = "OriginalID";

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
		case LOCATIONHISTORY_ADDRESSLINES:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("addresslines='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_ADMINAREA:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("adminarea='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_COUNTRYCODE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("countrycode='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_COUNTRYNAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("countryname='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_FEATURENAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("featurename='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_LOCALE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("locale='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_LOCALITY:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("locality='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_PHONE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("phone='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_POSTALCODE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("postalcode='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_PREMISES:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("premises='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_SUBADMINAREA:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("subadminarea='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_SUBLOCALITY:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("sublocality='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_SUBTHOROUGHFARE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("subthoroughfare='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_THOROUGHFARE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("thoroughfare='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_ADDRESSURL:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("addressurl='" + url.getPathSegments().get(2) + "'");
			break;
		case LOCATIONHISTORY_MAPTILEURLSMALL:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("maptileurlsmall='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_MAPTILEFILESMALL:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("maptilefilesmall='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_MAPTILEURLMEDIUM:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("maptileurlmedium='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_MAPTILEFILEMEDIUM:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("maptilefilemedium='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_MAPTILEURLLARGE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("maptileurllarge='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_MAPTILEFILELARGE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("maptilefilelarge='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_ORIGINALLATITUDE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("originallatitude='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_ORIGINALLONGITUDE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("originallongitude='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case LOCATIONHISTORY_ORIGINALID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("originalid='" + url.getPathSegments().get(2) + "'");
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
		case LOCATIONHISTORY_ADDRESSLINES:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_ADMINAREA:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_COUNTRYCODE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_COUNTRYNAME:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_FEATURENAME:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_LOCALE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_LOCALITY:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_PHONE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_POSTALCODE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_PREMISES:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_SUBADMINAREA:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_SUBLOCALITY:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_SUBTHOROUGHFARE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_THOROUGHFARE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_ADDRESSURL:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_MAPTILEURLSMALL:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_MAPTILEFILESMALL:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_MAPTILEURLMEDIUM:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_MAPTILEFILEMEDIUM:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_MAPTILEURLLARGE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_MAPTILEFILELARGE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_ORIGINALLATITUDE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_ORIGINALLONGITUDE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.locationhistory";
		case LOCATIONHISTORY_ORIGINALID:
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
		case LOCATIONHISTORY_ADDRESSLINES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"addresslines="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ADMINAREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"adminarea="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_COUNTRYCODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"countrycode="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_COUNTRYNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"countryname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_FEATURENAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"featurename="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_LOCALE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"locale="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_LOCALITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"locality="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_PHONE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"phone="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_POSTALCODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"postalcode="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_PREMISES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"premises="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_SUBADMINAREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"subadminarea="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_SUBLOCALITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"sublocality="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_SUBTHOROUGHFARE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"subthoroughfare="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_THOROUGHFARE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"thoroughfare="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ADDRESSURL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"addressurl="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEURLSMALL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"maptileurlsmall="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEFILESMALL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"maptilefilesmall="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEURLMEDIUM:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"maptileurlmedium="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEFILEMEDIUM:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"maptilefilemedium="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEURLLARGE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"maptileurllarge="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEFILELARGE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"maptilefilelarge="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ORIGINALLATITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"originallatitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ORIGINALLONGITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"originallongitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ORIGINALID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"originalid="
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
		case LOCATIONHISTORY_ADDRESSLINES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"addresslines="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ADMINAREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"adminarea="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_COUNTRYCODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"countrycode="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_COUNTRYNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"countryname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_FEATURENAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"featurename="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_LOCALE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"locale="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_LOCALITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"locality="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_PHONE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"phone="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_POSTALCODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"postalcode="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_PREMISES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"premises="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_SUBADMINAREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"subadminarea="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_SUBLOCALITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"sublocality="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_SUBTHOROUGHFARE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"subthoroughfare="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_THOROUGHFARE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"thoroughfare="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ADDRESSURL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"addressurl="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEURLSMALL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"maptileurlsmall="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEFILESMALL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"maptilefilesmall="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEURLMEDIUM:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"maptileurlmedium="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEFILEMEDIUM:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"maptilefilemedium="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEURLLARGE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"maptileurllarge="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_MAPTILEFILELARGE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"maptilefilelarge="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ORIGINALLATITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"originallatitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ORIGINALLONGITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"originallongitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case LOCATIONHISTORY_ORIGINALID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"originalid="
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
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/addresslines" + "/*", LOCATIONHISTORY_ADDRESSLINES);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/adminarea"
				+ "/*", LOCATIONHISTORY_ADMINAREA);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/countrycode"
				+ "/*", LOCATIONHISTORY_COUNTRYCODE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/countryname"
				+ "/*", LOCATIONHISTORY_COUNTRYNAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/featurename"
				+ "/*", LOCATIONHISTORY_FEATURENAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/locale"
				+ "/*", LOCATIONHISTORY_LOCALE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/locality"
				+ "/*", LOCATIONHISTORY_LOCALITY);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/phone"
				+ "/*", LOCATIONHISTORY_PHONE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/postalcode"
				+ "/*", LOCATIONHISTORY_POSTALCODE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/premises"
				+ "/*", LOCATIONHISTORY_PREMISES);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/subadminarea" + "/*", LOCATIONHISTORY_SUBADMINAREA);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/sublocality"
				+ "/*", LOCATIONHISTORY_SUBLOCALITY);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/subthoroughfare" + "/*", LOCATIONHISTORY_SUBTHOROUGHFARE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/thoroughfare" + "/*", LOCATIONHISTORY_THOROUGHFARE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/addressurl"
				+ "/*", LOCATIONHISTORY_ADDRESSURL);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/maptileurlsmall" + "/*", LOCATIONHISTORY_MAPTILEURLSMALL);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/maptilefilesmall" + "/*", LOCATIONHISTORY_MAPTILEFILESMALL);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/maptileurlmedium" + "/*", LOCATIONHISTORY_MAPTILEURLMEDIUM);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/maptilefilemedium" + "/*",
				LOCATIONHISTORY_MAPTILEFILEMEDIUM);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/maptileurllarge" + "/*", LOCATIONHISTORY_MAPTILEURLLARGE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/maptilefilelarge" + "/*", LOCATIONHISTORY_MAPTILEFILELARGE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/originallatitude" + "/*", LOCATIONHISTORY_ORIGINALLATITUDE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/originallongitude" + "/*",
				LOCATIONHISTORY_ORIGINALLONGITUDE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/originalid"
				+ "/*", LOCATIONHISTORY_ORIGINALID);

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
		LOCATIONHISTORY_PROJECTION_MAP.put(ADDRESSLINES, "addresslines");
		LOCATIONHISTORY_PROJECTION_MAP.put(ADMINAREA, "adminarea");
		LOCATIONHISTORY_PROJECTION_MAP.put(COUNTRYCODE, "countrycode");
		LOCATIONHISTORY_PROJECTION_MAP.put(COUNTRYNAME, "countryname");
		LOCATIONHISTORY_PROJECTION_MAP.put(FEATURENAME, "featurename");
		LOCATIONHISTORY_PROJECTION_MAP.put(LOCALE, "locale");
		LOCATIONHISTORY_PROJECTION_MAP.put(LOCALITY, "locality");
		LOCATIONHISTORY_PROJECTION_MAP.put(PHONE, "phone");
		LOCATIONHISTORY_PROJECTION_MAP.put(POSTALCODE, "postalcode");
		LOCATIONHISTORY_PROJECTION_MAP.put(PREMISES, "premises");
		LOCATIONHISTORY_PROJECTION_MAP.put(SUBADMINAREA, "subadminarea");
		LOCATIONHISTORY_PROJECTION_MAP.put(SUBLOCALITY, "sublocality");
		LOCATIONHISTORY_PROJECTION_MAP.put(SUBTHOROUGHFARE, "subthoroughfare");
		LOCATIONHISTORY_PROJECTION_MAP.put(THOROUGHFARE, "thoroughfare");
		LOCATIONHISTORY_PROJECTION_MAP.put(ADDRESSURL, "addressurl");
		LOCATIONHISTORY_PROJECTION_MAP.put(MAPTILEURLSMALL, "maptileurlsmall");
		LOCATIONHISTORY_PROJECTION_MAP
				.put(MAPTILEFILESMALL, "maptilefilesmall");
		LOCATIONHISTORY_PROJECTION_MAP
				.put(MAPTILEURLMEDIUM, "maptileurlmedium");
		LOCATIONHISTORY_PROJECTION_MAP.put(MAPTILEFILEMEDIUM,
				"maptilefilemedium");
		LOCATIONHISTORY_PROJECTION_MAP.put(MAPTILEURLLARGE, "maptileurllarge");
		LOCATIONHISTORY_PROJECTION_MAP
				.put(MAPTILEFILELARGE, "maptilefilelarge");
		LOCATIONHISTORY_PROJECTION_MAP
				.put(ORIGINALLATITUDE, "originallatitude");
		LOCATIONHISTORY_PROJECTION_MAP.put(ORIGINALLONGITUDE,
				"originallongitude");
		LOCATIONHISTORY_PROJECTION_MAP.put(ORIGINALID, "originalid");

	}
}
