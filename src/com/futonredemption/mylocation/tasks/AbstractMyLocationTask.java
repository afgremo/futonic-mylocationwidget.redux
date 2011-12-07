package com.futonredemption.mylocation.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import android.content.Context;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;

public abstract class AbstractMyLocationTask extends ContextAwareCallable<MyLocationRetrievalState> {

	private final List<Future<MyLocationRetrievalState>> stateFuture = new ArrayList<Future<MyLocationRetrievalState>>();
	private final MyLocationRetrievalState currentState;
	
	public AbstractMyLocationTask(Context context, MyLocationRetrievalState state) {
		super(context);
		this.currentState = state;
	}
	
	public AbstractMyLocationTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context);
		this.stateFuture.add(state);
		this.currentState = null;
	}
	
	public AbstractMyLocationTask(Context context, Future<MyLocationRetrievalState> [] state) {
		super(context);
		final int len = state.length;
		for(int i = 0; i < len; i++) {
			this.stateFuture.add(state[i]);
		}
		this.currentState = null;
	}
	
	public final MyLocationRetrievalState call() throws Exception {
		Thread.currentThread().setName(this.getClass().getSimpleName());
		MyLocationRetrievalState state = null;
		try {
			if(! stateFuture.isEmpty()) {
				state = this.stateFuture.get(0).get();
			} else {
				state = currentState;
			}
			
			if(state.hasError()) {
				loadDataFromErrorState(state);
			} else {
				loadData(state);
			}
		} catch(Exception e) {
			state.setError(e);
			Debugging.e(e);
		}
		
		return state;
	}

	public void addFuture(Future<MyLocationRetrievalState> future) {
		this.stateFuture.add(future);
	}
	
	protected void waitForAllFutures() throws InterruptedException, ExecutionException {
		for(Future<MyLocationRetrievalState> stateIter : this.stateFuture) {
			stateIter.get();
		}
	}
	
	protected void loadDataFromErrorState(final MyLocationRetrievalState state) {
		
	}
	
	protected abstract void loadData(final MyLocationRetrievalState state);
}
