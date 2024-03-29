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
		try {
			waitForAllFutures();
			Debugging.w("Save Location Bundle");
			MyLocationBundlePersistence persist = new MyLocationBundlePersistence(context);
			
			if(state.isNew()) {
				persist.insert(state.toBundleRecord());
			} else {
				persist.update(state.toBundleRecord());
			}
			
		} catch(Exception e) {
			Debugging.e(e);
		}
	}
}
