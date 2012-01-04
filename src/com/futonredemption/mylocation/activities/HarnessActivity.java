package com.futonredemption.mylocation.activities;

import com.futonredemption.mylocation.R;
import com.futonredemption.mylocation.adapters.PhoneFragmentsPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class HarnessActivity extends FragmentActivity {
	
	ViewPager pager;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        setViews();
	}

	private void setViews() {
		pager = (ViewPager)findViewById(R.id.pager);
		PagerAdapter adapter = new PhoneFragmentsPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
	}
}
