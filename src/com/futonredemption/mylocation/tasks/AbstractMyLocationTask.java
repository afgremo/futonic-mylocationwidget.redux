package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import android.content.Context;

import com.futonredemption.mylocation.MyLocationRetrievalState;

public abstract class AbstractMyLocationTask extends ContextAwareCallable<MyLocationRetrievalState> {

	private final Future<MyLocationRetrievalState> stateFuture;
	private final MyLocationRetrievalState currentState;
	
	public AbstractMyLocationTask(Context context, MyLocationRetrievalState state) {
		super(context);
		this.stateFuture = null;
		this.currentState = state;
	}
	
	public AbstractMyLocationTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context);
		this.stateFuture = state;
		this.currentState = null;
	}
	
	public final MyLocationRetrievalState call() throws Exception {
		
		MyLocationRetrievalState state = null;
		
		if(stateFuture != null) {
			state = this.stateFuture.get();
		} else {
			state = currentState;
		}
		
		loadData(state);
		
		return state;
	}

	protected abstract void loadData(final MyLocationRetrievalState state);
}
