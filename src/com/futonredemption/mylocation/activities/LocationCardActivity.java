package com.futonredemption.mylocation.activities;

import android.content.Intent;
import android.os.Bundle;

import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class LocationCardActivity extends MapActivity {

	MapView mapView;
	MapController mapController;
	private MyLocationBundle locationBundle;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationcard);
        setViews();
        handleIntent(getIntent());
    }
	
	private void handleIntent(Intent intent) {
		if(intent != null) {
			MyLocationBundle bundle = intent.getParcelableExtra("location");
			setLocationBundle(bundle);
		}
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private void setLocationBundle(MyLocationBundle bundle) {
		locationBundle = bundle;
		updateViews();
	}

	private void setViews() {
		mapView = (MapView)findViewById(R.id.mapview);
		mapController = mapView.getController();
	}
	private void updateViews() {
		GeoPoint point = locationBundle.toGeoPoint();
		mapController.setZoom(14);
		mapController.animateTo(point);
	}
}
