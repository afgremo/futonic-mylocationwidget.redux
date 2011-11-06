package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import android.content.Context;

import com.futonredemption.mylocation.MyLocationBundle;

public abstract class AbstractMyLocationTask extends ContextAwareCallable<MyLocationBundle> {

	private final Future<MyLocationBundle> bundle;
	public AbstractMyLocationTask(Context context, Future<MyLocationBundle> bundle) {
		super(context);
		this.bundle = bundle;
	}
	
	public final MyLocationBundle call() throws Exception {
		final MyLocationBundle locationBundle = this.bundle.get();
		
		if(locationBundle != null) {
			appendLocationData(locationBundle);
		} else {
			onEmptyLocation();
		}
		return locationBundle;
	}

	protected void onEmptyLocation() {
	}

	protected abstract void appendLocationData(final MyLocationBundle bundle);
}
