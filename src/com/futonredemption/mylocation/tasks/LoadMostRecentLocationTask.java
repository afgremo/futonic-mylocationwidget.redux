package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import android.content.Context;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.persistence.MyLocationBundlePersistence;
import com.futonredemption.mylocation.persistence.MyLocationBundleRecord;

public class LoadMostRecentLocationTask extends AbstractMyLocationTask {
	public LoadMostRecentLocationTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public LoadMostRecentLocationTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		Debugging.w("Obtain Bundle from Storage");
		
		final MyLocationBundlePersistence persist = new MyLocationBundlePersistence(context);
		Integer mostRecentId = persist.getMostRecentId();
		if(mostRecentId != null) {
			MyLocationBundleRecord record = persist.get(mostRecentId.intValue());
			
			if(record.hasBundle()) {
				state.setLocationId(mostRecentId);
				state.copyFrom(record.getBundle());
				state.setOriginalCoordinates(record.getOriginalCoordinates());
			}
		}
	}
}
