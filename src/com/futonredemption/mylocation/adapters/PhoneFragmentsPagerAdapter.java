package com.futonredemption.mylocation.adapters;

import com.futonredemption.mylocation.fragments.LocationHistoryFragment;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class PhoneFragmentsPagerAdapter extends FragmentPagerAdapter {

	
	public PhoneFragmentsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		return new LocationHistoryFragment();
	}

	@Override
	public int getCount() {
		return 10;
	}

}
