package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Callable;

import android.content.Context;

public abstract class ContextAwareCallable<V> implements Callable<V> {

	protected final Context context;
	
	public ContextAwareCallable(Context context) {
		this.context = context;
	}
}
