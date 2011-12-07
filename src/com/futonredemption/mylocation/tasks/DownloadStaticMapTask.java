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

		if(state.hasLocation() && ! state.hasStaticMap()) {
			Parameters params = new Parameters();
			params.center = new Center(state.getLocation());
			params.size = new Dimension(256,256);
			params.scale = 2;
			params.zoom = 10; //128x128, z=9 or 256x256, z=10?
			try {
				StaticMap map = new StaticMap();
				StaticMapsClient client = new StaticMapsClient();
				map = client.downloadMap(context, params);
				state.setStaticMap(map);
			} catch(Exception e) {
				Debugging.e(e);
			}
		}
	}
}
