package com.futonredemption.mylocation.activities;

import com.futonredemption.mylocation.R;
import com.google.android.maps.MapActivity;

import android.os.Bundle;

public class TabletActivity extends MapActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
