package com.futonredemption.mylocation.tasks;

import org.beryl.diagnostics.Logger;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public abstract class EventBasedContextAwareCallable<V> extends ContextAwareCallable<V> {

	V result = null;
	Looper looper = null;
	Handler handler = null;
	Exception error = null;
	
	public EventBasedContextAwareCallable(Context context) {
		super(context);
	}

	public final V call() throws Exception {
		Thread.currentThread().setName(this.getClass().getSimpleName());
		Looper.prepare();
		Looper mainLooper = Looper.getMainLooper();
		looper = Looper.myLooper();
		handler = new Handler(looper);
		
		Logger.w("Main Looper Same? " + Boolean.toString(looper == mainLooper));
		
		try {
			onBeginTask();
			Looper.loop();
		} catch(Exception e) {
			finishWithError(e);
			Logger.e(e);
		} finally {
			onFinishTask();
		}
		
		return this.result;
	}

	protected abstract void onBeginTask();
	protected abstract void onFinishTask();
	
	public void finish() {
		looper.quit();
	}
	
	public void finishWithResult(V result) {
		this.result = result;
		looper.quit();
	}
	
	public void finishWithError(Exception error) {
		this.result = null;
		this.error = error;
		looper.quit();
	}
}
