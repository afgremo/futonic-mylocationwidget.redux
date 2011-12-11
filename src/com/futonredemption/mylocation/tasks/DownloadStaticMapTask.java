package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.StaticMap;
import com.futonredemption.mylocation.google.maps.Parameters;
import com.futonredemption.mylocation.google.maps.Parameters.Center;
import com.futonredemption.mylocation.google.maps.Parameters.Dimension;
import com.futonredemption.mylocation.google.maps.StaticMapsClient;

import android.content.Context;

public class DownloadStaticMapTask extends AbstractMyLocationTask {

	public DownloadStaticMapTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public DownloadStaticMapTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {

		if(state.hasLocation() && ! state.hasAllStaticMaps()) {
			Parameters params = new Parameters();
			params.center = new Center(state.getLocation());
			try {
				StaticMap map = state.getStaticMap();
				
				if(map == null) {
					map = new StaticMap();
					state.setStaticMap(map);
				}
				
				if(!state.hasSmallStaticMap()) {
					downloadSmallMap(map, params);
				}
				
				if(!state.hasMediumStaticMap()) {
					downloadMediumMap(map, params);
				}
				
				if(! state.hasLargeStaticMap()) {
					downloadLargeMap(map, params);
				}
			} catch(Exception e) {
				Debugging.e(e);
			}
		}
	}

	private void downloadSmallMap(StaticMap map, Parameters params) {
		final StaticMapsClient client = new StaticMapsClient();
		params.size = new Dimension(128,128);
		params.scale = 1;
		params.zoom = 19; //128x128, z=9 or 256x256, z=10?
		if(client.downloadMap(context, params)) {
			map.setSmallMapUrl(client.getUrl());
			map.setSmallMapFilePath(client.getFilePath());
		}
	}
	
	private void downloadMediumMap(StaticMap map, Parameters params) {
		final StaticMapsClient client = new StaticMapsClient();
		params.size = new Dimension(256,256);
		params.scale = 2;
		params.zoom = 18; //128x128, z=9 or 256x256, z=10?
		if(client.downloadMap(context, params)) {
			map.setMediumMapUrl(client.getUrl());
			map.setMediumMapFilePath(client.getFilePath());
		}
	}

	private void downloadLargeMap(StaticMap map, Parameters params) {
		final StaticMapsClient client = new StaticMapsClient();
		params.size = new Dimension(512,512);
		params.scale = 2;
		params.zoom = 17; //128x128, z=9 or 256x256, z=10?
		if(client.downloadMap(context, params)) {
			map.setLargeMapUrl(client.getUrl());
			map.setLargeMapFilePath(client.getFilePath());
		}
	}
}
