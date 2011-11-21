package com.futonredemption.mylocation;

import java.util.Locale;

import org.beryl.location.LocationMonitor;

import com.futonredemption.mylocation.activities.LocationCardActivity;
import com.futonredemption.mylocation.exceptions.CannotObtainAccurateFixException;
import com.futonredemption.mylocation.exceptions.NoLocationProvidersEnabledException;
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
		return state.getLocation().getLatitude();
	}
	
	public double getLongitude() {
		return state.getLocation().getLongitude();
	}
	
	public CharSequence getTitle() {
		CharSequence title = new String();
		
		if(isLoading()) {
			title = context.getText(R.string.finding_location);
		} else if(isAvailable()) {
			if(state.hasAddress()) {
				final Address address = state.getAddress();
				if(address.getMaxAddressLineIndex() > 0) {
					title = address.getAddressLine(0);
				}
			}
			else if (state.hasLocation()) {
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
			if(state.hasAddress()) {
				final Address address = state.getAddress();
				if(address.getMaxAddressLineIndex() > 1) {
					description = address.getAddressLine(1);
				}
				else {
					description = address.getFeatureName();
				}
			}
			else if (state.hasLocation()){
				description = getOneLineCoordinates();
			} else {
				description = context.getText(R.string.finding_location);
			}
		} else if (state.isLoading()) {
			if(state.hasLocation()) {
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

	public CharSequence getText(int resId) {
		return context.getText(resId);
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
	
	public PendingIntent getPendingOpenLocationCardAction() {
		return convertToPendingActivity(context, getOpenLocationCardAction());
	}

	private Intent getOpenLocationCardAction() {
		final Intent intent = new Intent(context, LocationCardActivity.class);
		intent.putExtra("location", this.state.getLocationBundle());
		return intent;
	}

	public PendingIntent getPendingShareLocationAction() {
		return convertToPendingActivity(context, getShareLocationAction());
	}
	
	private Intent getShareLocationAction() {
		// TODO: This isn't complete.
		final Intent intent = new Intent(Intent.ACTION_SEND);
		
		return intent;
	}

	public static PendingIntent convertToPendingService(final Context context, final Intent intent) {
		return PendingIntent.getService(context, intent.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	public static PendingIntent convertToPendingActivity(final Context context, final Intent intent) {
		return PendingIntent.getActivity(context, intent.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	public boolean isGpsLocationDisabled() {
		final LocationMonitor lm = new LocationMonitor(context);
		return lm.isGpsSupported() && !lm.isGpsEnabled();
	}

	public boolean isNetworkLocationDisabled() {
		final LocationMonitor lm = new LocationMonitor(context);
		return lm.isNetworkSupported() && !lm.isNetworkEnabled();
	}

	public boolean isGpsLocationEnabled() {
		final LocationMonitor lm = new LocationMonitor(context);
		return lm.isGpsSupported() && lm.isGpsEnabled();
	}

	public CharSequence getErrorDescription() {
		CharSequence result = "";
		
		if(state.hasError()) {
			if(state.isError(CannotObtainAccurateFixException.class)) {
				result = getText(R.string.error_its_taking_too_long_to_get_a_fix);
			} else if(state.isError(NoLocationProvidersEnabledException.class)) {
				result = getText(R.string.error_location_is_disabled);
			} else {
				result = getText(R.string.error_something_went_wrong_try_again);
			}
		}
		return result;
	}

	public CharSequence getErrorTitle() {
		CharSequence result = "";
		
		if(state.hasError()) {
			if(state.isError(CannotObtainAccurateFixException.class)) {
				result = getText(R.string.error_its_taking_too_long_to_get_a_fix);
			} else if(state.isError(NoLocationProvidersEnabledException.class)) {
				result = getText(R.string.error_location_is_disabled);
			} else {
				result = getText(R.string.error_something_went_wrong_try_again);
			}
		}
		return result;
	}
}
