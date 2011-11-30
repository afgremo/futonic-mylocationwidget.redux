package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import org.beryl.diagnostics.Logger;

import android.content.Context;

import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.persistence.MyLocationBundlePersistence;

public class LoadMostRecentLocationTask extends AbstractMyLocationTask {
	public LoadMostRecentLocationTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public LoadMostRecentLocationTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		Logger.w("Obtain Bundle from Storage");
		MyLocationBundlePersistence persist = new MyLocationBundlePersistence(context);
		MyLocationBundle locationBundle = persist.getMostRecent();
		state.copyFrom(locationBundle);
	}
}
