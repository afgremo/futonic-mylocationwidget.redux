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

public class AddressContentProvider extends ContentProvider {

	private MyLocationOpenHelper dbHelper;
	private static HashMap<String, String> ADDRESS_PROJECTION_MAP;
	private static final String TABLE_NAME = "address";
	private static final String AUTHORITY = "com.futonredemption.mylocation.provider.addresscontentprovider";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	public static final Uri _ID_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase());
	public static final Uri LOCATIONSID_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/locationsid");
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
	public static final Uri LATITUDE_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/latitude");
	public static final Uri LONGITUDE_FIELD_CONTENT_URI = Uri
			.parse("content://" + AUTHORITY + "/" + TABLE_NAME.toLowerCase()
					+ "/longitude");
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
	public static final Uri URL_FIELD_CONTENT_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + TABLE_NAME.toLowerCase() + "/url");

	public static final String DEFAULT_SORT_ORDER = "_id ASC";

	private static final UriMatcher URL_MATCHER;

	private static final int ADDRESS = 1;
	private static final int ADDRESS__ID = 2;
	private static final int ADDRESS_LOCATIONSID = 3;
	private static final int ADDRESS_ADDRESSLINES = 4;
	private static final int ADDRESS_ADMINAREA = 5;
	private static final int ADDRESS_COUNTRYCODE = 6;
	private static final int ADDRESS_COUNTRYNAME = 7;
	private static final int ADDRESS_FEATURENAME = 8;
	private static final int ADDRESS_LATITUDE = 9;
	private static final int ADDRESS_LONGITUDE = 10;
	private static final int ADDRESS_LOCALE = 11;
	private static final int ADDRESS_LOCALITY = 12;
	private static final int ADDRESS_PHONE = 13;
	private static final int ADDRESS_POSTALCODE = 14;
	private static final int ADDRESS_PREMISES = 15;
	private static final int ADDRESS_SUBADMINAREA = 16;
	private static final int ADDRESS_SUBLOCALITY = 17;
	private static final int ADDRESS_SUBTHOROUGHFARE = 18;
	private static final int ADDRESS_THOROUGHFARE = 19;
	private static final int ADDRESS_URL = 20;

	// Content values keys (using column names)
	public static final String _ID = "_id";
	public static final String LOCATIONSID = "LocationSID";
	public static final String ADDRESSLINES = "AddressLines";
	public static final String ADMINAREA = "AdminArea";
	public static final String COUNTRYCODE = "CountryCode";
	public static final String COUNTRYNAME = "CountryName";
	public static final String FEATURENAME = "FeatureName";
	public static final String LATITUDE = "Latitude";
	public static final String LONGITUDE = "Longitude";
	public static final String LOCALE = "Locale";
	public static final String LOCALITY = "Locality";
	public static final String PHONE = "Phone";
	public static final String POSTALCODE = "PostalCode";
	public static final String PREMISES = "Premises";
	public static final String SUBADMINAREA = "SubAdminArea";
	public static final String SUBLOCALITY = "SubLocality";
	public static final String SUBTHOROUGHFARE = "SubThoroughfare";
	public static final String THOROUGHFARE = "Thoroughfare";
	public static final String URL = "Url";

	public boolean onCreate() {
		dbHelper = new MyLocationOpenHelper(getContext(), true);
		return (dbHelper == null) ? false : true;
	}

	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sort) {
		SQLiteDatabase mDB = dbHelper.getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (URL_MATCHER.match(url)) {
		case ADDRESS:
			qb.setTables(TABLE_NAME);
			qb.setProjectionMap(ADDRESS_PROJECTION_MAP);
			break;
		case ADDRESS__ID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("_id=" + url.getPathSegments().get(1));
			break;
		case ADDRESS_LOCATIONSID:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("locationsid='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_ADDRESSLINES:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("addresslines='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case ADDRESS_ADMINAREA:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("adminarea='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_COUNTRYCODE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("countrycode='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_COUNTRYNAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("countryname='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_FEATURENAME:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("featurename='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_LATITUDE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("latitude='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_LONGITUDE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("longitude='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_LOCALE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("locale='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_LOCALITY:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("locality='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_PHONE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("phone='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_POSTALCODE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("postalcode='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_PREMISES:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("premises='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_SUBADMINAREA:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("subadminarea='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case ADDRESS_SUBLOCALITY:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("sublocality='" + url.getPathSegments().get(2) + "'");
			break;
		case ADDRESS_SUBTHOROUGHFARE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("subthoroughfare='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case ADDRESS_THOROUGHFARE:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("thoroughfare='" + url.getPathSegments().get(2)
					+ "'");
			break;
		case ADDRESS_URL:
			qb.setTables(TABLE_NAME);
			qb.appendWhere("url='" + url.getPathSegments().get(2) + "'");
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
		case ADDRESS:
			return "vnd.android.cursor.dir/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS__ID:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_LOCATIONSID:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_ADDRESSLINES:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_ADMINAREA:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_COUNTRYCODE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_COUNTRYNAME:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_FEATURENAME:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_LATITUDE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_LONGITUDE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_LOCALE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_LOCALITY:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_PHONE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_POSTALCODE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_PREMISES:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_SUBADMINAREA:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_SUBLOCALITY:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_SUBTHOROUGHFARE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_THOROUGHFARE:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";
		case ADDRESS_URL:
			return "vnd.android.cursor.item/vnd.com.futonredemption.mylocation.provider.address";

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
		if (URL_MATCHER.match(url) != ADDRESS) {
			throw new IllegalArgumentException("Unknown URL " + url);
		}

		rowID = mDB.insert("address", "address", values);
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
		case ADDRESS:
			count = mDB.delete(TABLE_NAME, where, whereArgs);
			break;
		case ADDRESS__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.delete(TABLE_NAME,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LOCATIONSID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"locationsid="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_ADDRESSLINES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"addresslines="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_ADMINAREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"adminarea="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_COUNTRYCODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"countrycode="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_COUNTRYNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"countryname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_FEATURENAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"featurename="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LATITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"latitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LONGITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"longitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LOCALE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"locale="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LOCALITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"locality="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_PHONE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"phone="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_POSTALCODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"postalcode="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_PREMISES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"premises="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_SUBADMINAREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"subadminarea="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_SUBLOCALITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"sublocality="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_SUBTHOROUGHFARE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"subthoroughfare="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_THOROUGHFARE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"thoroughfare="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_URL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.delete(TABLE_NAME,
					"url="
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
		case ADDRESS:
			count = mDB.update(TABLE_NAME, values, where, whereArgs);
			break;
		case ADDRESS__ID:
			segment = url.getPathSegments().get(1);
			count = mDB.update(TABLE_NAME, values,
					"_id="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LOCATIONSID:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"locationsid="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_ADDRESSLINES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"addresslines="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_ADMINAREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"adminarea="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_COUNTRYCODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"countrycode="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_COUNTRYNAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"countryname="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_FEATURENAME:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"featurename="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LATITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"latitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LONGITUDE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"longitude="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LOCALE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"locale="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_LOCALITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"locality="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_PHONE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"phone="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_POSTALCODE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"postalcode="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_PREMISES:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"premises="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_SUBADMINAREA:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"subadminarea="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_SUBLOCALITY:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"sublocality="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_SUBTHOROUGHFARE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"subthoroughfare="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_THOROUGHFARE:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"thoroughfare="
							+ segment
							+ (!TextUtils.isEmpty(where) ? " AND (" + where
									+ ')' : ""), whereArgs);
			break;
		case ADDRESS_URL:
			segment = "'" + url.getPathSegments().get(2) + "'";
			count = mDB.update(TABLE_NAME, values,
					"url="
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
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase(), ADDRESS);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/#",
				ADDRESS__ID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/locationsid"
				+ "/*", ADDRESS_LOCATIONSID);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/addresslines" + "/*", ADDRESS_ADDRESSLINES);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/adminarea"
				+ "/*", ADDRESS_ADMINAREA);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/countrycode"
				+ "/*", ADDRESS_COUNTRYCODE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/countryname"
				+ "/*", ADDRESS_COUNTRYNAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/featurename"
				+ "/*", ADDRESS_FEATURENAME);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/latitude"
				+ "/*", ADDRESS_LATITUDE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/longitude"
				+ "/*", ADDRESS_LONGITUDE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/locale"
				+ "/*", ADDRESS_LOCALE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/locality"
				+ "/*", ADDRESS_LOCALITY);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/phone"
				+ "/*", ADDRESS_PHONE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/postalcode"
				+ "/*", ADDRESS_POSTALCODE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/premises"
				+ "/*", ADDRESS_PREMISES);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/subadminarea" + "/*", ADDRESS_SUBADMINAREA);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/sublocality"
				+ "/*", ADDRESS_SUBLOCALITY);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/subthoroughfare" + "/*", ADDRESS_SUBTHOROUGHFARE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase()
				+ "/thoroughfare" + "/*", ADDRESS_THOROUGHFARE);
		URL_MATCHER.addURI(AUTHORITY, TABLE_NAME.toLowerCase() + "/url" + "/*",
				ADDRESS_URL);

		ADDRESS_PROJECTION_MAP = new HashMap<String, String>();
		ADDRESS_PROJECTION_MAP.put(_ID, "_id");
		ADDRESS_PROJECTION_MAP.put(LOCATIONSID, "locationsid");
		ADDRESS_PROJECTION_MAP.put(ADDRESSLINES, "addresslines");
		ADDRESS_PROJECTION_MAP.put(ADMINAREA, "adminarea");
		ADDRESS_PROJECTION_MAP.put(COUNTRYCODE, "countrycode");
		ADDRESS_PROJECTION_MAP.put(COUNTRYNAME, "countryname");
		ADDRESS_PROJECTION_MAP.put(FEATURENAME, "featurename");
		ADDRESS_PROJECTION_MAP.put(LATITUDE, "latitude");
		ADDRESS_PROJECTION_MAP.put(LONGITUDE, "longitude");
		ADDRESS_PROJECTION_MAP.put(LOCALE, "locale");
		ADDRESS_PROJECTION_MAP.put(LOCALITY, "locality");
		ADDRESS_PROJECTION_MAP.put(PHONE, "phone");
		ADDRESS_PROJECTION_MAP.put(POSTALCODE, "postalcode");
		ADDRESS_PROJECTION_MAP.put(PREMISES, "premises");
		ADDRESS_PROJECTION_MAP.put(SUBADMINAREA, "subadminarea");
		ADDRESS_PROJECTION_MAP.put(SUBLOCALITY, "sublocality");
		ADDRESS_PROJECTION_MAP.put(SUBTHOROUGHFARE, "subthoroughfare");
		ADDRESS_PROJECTION_MAP.put(THOROUGHFARE, "thoroughfare");
		ADDRESS_PROJECTION_MAP.put(URL, "url");

	}
}
