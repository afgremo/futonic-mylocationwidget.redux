package com.futonredemption.mylocation;

import java.util.Locale;

import com.futonredemption.mylocation.services.WidgetUpdateService;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.provider.Settings;

public class DataToViewModelAdapter {

	final Context context;
	final MyLocationRetrievalState state;
	
	public DataToViewModelAdapter(final Context context, MyLocationRetrievalState state) {
		this.context = context;
		this.state = state;
	}
	
	public MyLocationRetrievalState getState() {
		return state;
	}
	
	public double getLatitude() {
		return state.bundle.getLocation().getLatitude();
	}
	
	public double getLongitude() {
		return state.bundle.getLocation().getLongitude();
	}
	
	public CharSequence getTitle() {
		CharSequence title = new String();
		
		if(isLoading()) {
			title = context.getText(R.string.finding_location);
		} else if(isAvailable()) {
			if(state.bundle.hasAddress()) {
				final Address address = state.bundle.getAddress();
				if(address.getMaxAddressLineIndex() > 0) {
					title = address.getAddressLine(0);
				}
			}
			else if (state.bundle.hasLocation()) {
				title = context.getText(R.string.coordinates);
			} else {
				title = context.getText(R.string.locating);
			}
		} else {
			title = context.getText(R.string.could_not_get_location);
		}
		
		return title;
	}
	
	public CharSequence getDescription() {
		CharSequence description = new String();
		
		if(state.isCompleted()) {
			if(state.bundle.hasAddress()) {
				final Address address = state.bundle.getAddress();
				if(address.getMaxAddressLineIndex() > 1) {
					description = address.getAddressLine(1);
				}
				else {
					description = address.getFeatureName();
				}
			}
			else if (state.bundle.hasLocation()){
				description = getOneLineCoordinates();
			} else {
				description = context.getText(R.string.finding_location);
			}
		} else if (state.isLoading()) {
			if(state.bundle.hasLocation()) {
				description = context.getText(R.string.address);
			} else {
				description = context.getText(R.string.coordinates);
			}
		}
		
		return description;
	}
	
	private CharSequence getOneLineCoordinates() {
		return String.format(Locale.ENGLISH, "Lat: %s Long: %s", getLatitude(), getLongitude());
	}

	public boolean isAvailable() {
		return state.isCompleted();
	}
	
	public boolean isLoading() {
		return state.isLoading();
	}

	public Intent getRefreshAction() {
		final Intent intent = WidgetUpdateService.getBeginFullUpdate(context);
		return intent;
	}
	
	public Intent getOpenLocationSettingsAction() {
		return new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	}
	
	public PendingIntent getPendingRefreshAction() {
		return convertToPendingService(context, getRefreshAction());
	}
	
	public PendingIntent getPendingOpenLocationSettingsAction() {
		return convertToPendingActivity(context, getOpenLocationSettingsAction());
	}
	
	
	
	public static PendingIntent convertToPendingService(final Context context, final Intent intent) {
		return PendingIntent.getService(context, intent.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	public static PendingIntent convertToPendingActivity(final Context context, final Intent intent) {
		return PendingIntent.getActivity(context, intent.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
