package com.futonredemption.mylocation.google.maps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.beryl.diagnostics.Logger;

import com.futonredemption.mylocation.StaticMap;

import android.net.Uri;
import android.os.Bundle;

public class StaticMapsClient {

	private static final String StaticMapsUri = "http://maps.googleapis.com/maps/api/staticmap";

	public StaticMap downloadMap(File fileDesc, Parameters renderParams) {
		StaticMap map = new StaticMap();

		Bundle httpParams = fromParameters(renderParams);
		HttpURLConnection connection = null;
		Uri uri = buildUri(httpParams);
		String urlString = uri.toString();

		
		try {
			Logger.w(urlString);
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();

			FileOutputStream fos = new FileOutputStream(fileDesc);
			InputStream is = connection.getInputStream();

			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len1);
			}

			fos.close();
			is.close();
			map.setUrl(urlString);
			map.setFilePath(fileDesc.getAbsolutePath());
		} catch (IOException e) {
			Logger.e(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		
		return map;
	}

	protected Uri buildUri(Bundle params) {
		Uri.Builder builder = Uri.parse(StaticMapsUri).buildUpon();

		try {
			for (String key : params.keySet()) {
				String value = params.getString(key);
				if (value != null) {
					builder.appendQueryParameter(key, value);
				}
			}
		} catch (Exception e) {
			Logger.e(e);
		}

		return builder.build();
	}

	protected Bundle fromParameters(Parameters params) {
		final Bundle result = new Bundle();
		result.putString("center", params.center.toString());
		result.putString("zoom", Integer.toString(params.zoom));
		result.putString("size", params.size.toString());
		result.putString("scale", Integer.toString(params.scale));
		result.putString("maptype", params.maptype);
		result.putString("language", params.language);
		result.putString("markers", params.markers);
		result.putString("path", params.path);
		result.putString("visible", Boolean.toString(params.visible));
		result.putString("style", params.style);
		result.putString("sensor", Boolean.toString(params.sensor));

		return result;
	}
}
