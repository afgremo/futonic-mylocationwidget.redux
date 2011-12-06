package com.futonredemption.mylocation;

import org.beryl.diagnostics.Logger;

import android.os.Debug;

/** Collection of utility methods for debugging an Android system. */
public class Debugging {

	private static final boolean debugMode = true;
	
	/** Pauses the current thread for 10 seconds. */
	public static final void haltForAWhile() {
		haltForAWhile(10);
	}

	/** Pauses the current thread for a specified amount of seconds. */
	public static final void haltForAWhile(int seconds) {
		
		if(!debugMode) return;
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Wait for the debugger to settle and pause the debugger here. */
	public static void breakpoint() {
		if(!debugMode) return;
		Debug.waitForDebugger();
	}
	
	public static void e(Exception ex) {
		Logger.e(ex);
	}
	
	public static void e(String message) {
		Logger.e(message);
	}
	
	public static void w(String message) {
		Logger.w(message);
	}
}
