package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import com.futonredemption.mylocation.MyLocationRetrievalState;
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

		if(state.bundle.hasLocation()) {
			Parameters params = new Parameters();
			params.center = new Center(state.bundle.getLocation());
			params.size = new Dimension(128,128);
			params.scale = 2;
			params.zoom = 9;
			StaticMapsClient client = new StaticMapsClient();
			client.downloadMap("map.png", params);
		}
	}
}
