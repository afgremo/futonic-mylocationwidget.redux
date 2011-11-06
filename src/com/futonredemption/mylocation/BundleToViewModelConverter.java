package com.futonredemption.mylocation;

public class BundleToViewModelConverter {

	final MyLocationBundle bundle;
	
	public BundleToViewModelConverter(MyLocationBundle bundle) {
		this.bundle = bundle;
	}
	
	public CharSequence getLine1() {
		return "";
	}
	
	public CharSequence getLine2() {
		return "";
	}
}
