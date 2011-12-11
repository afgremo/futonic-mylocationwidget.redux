package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import android.content.Context;
import android.location.Location;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.OriginalCoordinates;
import com.futonredemption.mylocation.persistence.MyLocationBundlePersistence;
import com.futonredemption.mylocation.persistence.MyLocationBundleRecord;

/** If there's another location that is close enough to the current location then fill the current location. */
public class LoadCloseEnoughDataTask extends AbstractMyLocationTask {

	public LoadCloseEnoughDataTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public LoadCloseEnoughDataTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		Debugging.w("LoadCloseEnoughDataTask, reading from cache...");
		if(state.hasLocation() && state.hasMissingData()) {
			final Location location = state.getLocation();
			final MyLocationBundlePersistence persist = new MyLocationBundlePersistence(context);
			final OriginalCoordinates coords = persist.getCloseEnough(location.getLatitude(), location.getLongitude());
			
			// If there's data from the cache then get the information.
			if(coords != null) {
				final MyLocationBundleRecord record = persist.get(coords.getId());
				if(record.hasBundle()) {
					state.setOriginalCoordinates(coords);
					state.fillFrom(record.getBundle());
				}
			}
		}
	}
}
