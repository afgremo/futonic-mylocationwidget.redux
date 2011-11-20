package com.futonredemption.mylocation;

public class Debugging {

	public static final void haltForAWhile() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
