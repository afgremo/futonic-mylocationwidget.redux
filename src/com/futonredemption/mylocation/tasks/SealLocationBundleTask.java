package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import android.content.Context;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;

public class SealLocationBundleTask extends AbstractMyLocationTask {
	public SealLocationBundleTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public SealLocationBundleTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		Debugging.w("Seal Bundle");
		
		state.seal();
	}
}
