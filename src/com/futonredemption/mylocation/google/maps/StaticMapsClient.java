package com.futonredemption.mylocation.google.maps;

import java.io.File;

import org.beryl.net.SimpleFileDownloader;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.StaticMap;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

public class StaticMapsClient {

	private static final String StaticMapsUri = "http://maps.googleapis.com/maps/api/staticmap";

	public StaticMap downloadMap(Context context, Parameters renderParams) {
		StaticMap map = new StaticMap();

		Bundle httpParams = fromParameters(renderParams);
		Uri uri = buildUri(httpParams);
		String urlString = uri.toString();

		SimpleFileDownloader downloader = new SimpleFileDownloader();
		File mapFile = downloader.download(context, urlString, ".png");
		if(mapFile != null) {
			map.setUrl(urlString);
			map.setFilePath(mapFile.getAbsolutePath());
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
			Debugging.e(e);
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
