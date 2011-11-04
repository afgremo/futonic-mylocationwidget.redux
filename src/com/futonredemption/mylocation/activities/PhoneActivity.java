package com.futonredemption.mylocation.activities;

import org.beryl.concurrent.WatchdogTimer;

import com.futonredemption.mylocation.R;
import com.futonredemption.mylocation.services.WidgetUpdateService;
import com.google.android.maps.MapActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PhoneActivity extends MapActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        WatchdogTimer timer = new WatchdogTimer();
        timer.run();
        */
        Intent intent = new Intent(this, WidgetUpdateService.class);
        this.startService(intent);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
