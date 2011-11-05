package com.futonredemption.mylocation.activities;

import com.futonredemption.mylocation.R;
import com.futonredemption.mylocation.services.WidgetUpdateService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PhoneActivity extends Activity {

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

}
