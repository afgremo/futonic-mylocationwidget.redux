package com.futonredemption.mylocation.persistence;

import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.OriginalCoordinates;

public class MyLocationBundleRecord {

	private MyLocationBundle bundle;
	private Integer id;
	private OriginalCoordinates originalCoordinates;
	
	public boolean hasBundle() {
		return bundle != null;
	}
	
	public MyLocationBundle getBundle() {
		return bundle;
	}
	
	public void setBundle(MyLocationBundle bundle) {
		this.bundle = bundle;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public OriginalCoordinates getOriginalCoordinates() {
		return originalCoordinates;
	}
	
	public void setOriginalCoordinates(OriginalCoordinates originalCoordinates) {
		this.originalCoordinates = originalCoordinates;
	}
}
