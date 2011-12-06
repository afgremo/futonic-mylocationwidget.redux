package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import android.content.Context;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.persistence.MyLocationBundlePersistence;

public class SaveLocationBundleTask extends AbstractMyLocationTask {
	public SaveLocationBundleTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public SaveLocationBundleTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		Debugging.w("Update Widgets");
		MyLocationBundlePersistence persist = new MyLocationBundlePersistence(context);
		
		if(state.isNew()) {
			persist.save(state.getLocationBundle());
		} else {
			// TODO: Use previous id to update information.
		}
	}
}
