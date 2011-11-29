package com.futonredemption.mylocation;

import android.os.Debug;

public class Debugging {

	private static final boolean debugMode = true;
	public static final void haltForAWhile() {
		
		if(!debugMode) return;
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void breakpoint() {
		if(!debugMode) return;
		Debug.waitForDebugger();
	}
}
