package com.futonredemption.mylocation;

import org.beryl.location.LocationMonitor;

import com.futonredemption.mylocation.activities.LocationCardActivity;
import com.futonredemption.mylocation.activities.PhoneActivity;
import com.futonredemption.mylocation.activities.TabletActivity;
import com.futonredemption.mylocation.exceptions.CannotObtainAccurateFixException;
import com.futonredemption.mylocation.exceptions.NoLocationProvidersEnabledException;
import com.futonredemption.mylocation.services.WidgetUpdateService;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;
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
	
	/** Has enough information to start displaying data to the user. Some data may still be missing. */
	public boolean hasBasicInformation() {
		return state.hasAddress() && state.hasShortMapUrls();
	}
	
	public Uri getSmallStaticMapFileUri() {
		Uri uri = null;
		StaticMap staticMap = state.getStaticMap();
		if(staticMap != null && staticMap.getSmallMapFilePath() != null) {
			uri = Uri.parse(staticMap.getSmallMapFilePath());
		}
		return uri;
	}
	
	public Uri getMediumStaticMapFileUri() {
		Uri uri = null;
		StaticMap staticMap = state.getStaticMap();
		if(staticMap != null && staticMap.getMediumMapFilePath() != null) {
			uri = Uri.parse(staticMap.getMediumMapFilePath());
		}
		return uri;
	}
	
	public Uri getLargeStaticMapFileUri() {
		Uri uri = null;
		StaticMap staticMap = state.getStaticMap();
		if(staticMap != null && staticMap.getLargeMapFilePath() != null) {
			uri = Uri.parse(staticMap.getLargeMapFilePath());
		}
		return uri;
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
	
	public CharSequence getTickerText() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTitle());
		sb.append(" ");
		sb.append(getDescription());
		
		return sb.toString();
	}
	
	private String getOneLineCoordinates() {
		return this.state.getOneLineCoordinates();
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

	// If there's no location then we just started processing.
	public boolean hasJustStarted() {
		return ! state.hasLocation() && ! state.hasError();
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

	public Intent getOpenLocationCardAction() {
		return LocationCardActivity.getActivityIntent(context, state.getLocationBundle());
	}

	public PendingIntent getPendingShareLocationAction() {
		return convertToPendingActivity(context, getShareLocationAction());
	}
	
	public PendingIntent getPendingMainActivityAction() {
		return convertToPendingActivity(context, getMainActivityAction());
	}
	
	public Intent getMainActivityAction() {
		if(context.getResources().getBoolean(R.bool.IsTablet)) {
			return getTabletMainActivityAction();
		} else {
			return getPhoneMainActivityAction();
		}
	}
	public Intent getTabletMainActivityAction() {
		return new Intent(context, TabletActivity.class);
	}
	public Intent getPhoneMainActivityAction() {
		return new Intent(context, PhoneActivity.class);
	}
	
	public static final String MimeEmail = "text/plain";
	
	public CharSequence getEmailShareContent() {
		final StringBuilder sb = new StringBuilder();
		final String address = getOneLineAddress();
		
		
		if(address != null) {
			sb.append(address);
		} else {
			final String coordinates = getOneLineCoordinates();
			sb.append(coordinates);
		}
		
		if(state.hasBasicShortMapUrl()) {
			sb.append(" ");
			sb.append(state.getBasicShortMapUrl());
		}
			
		return sb.toString();
	}
	
	private String getOneLineAddress() {
		return state.getOneLineAddress();
	}

	public Intent getShareLocationAction() {
		final Intent intent = new Intent(Intent.ACTION_SEND);
		
		CharSequence subject = getText(R.string.share_address_subject);
		CharSequence content = getEmailShareContent();
		intent.setType(MimeEmail);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		
		return intent;
	}

	public Uri getSmallWidgetMapUri() {
		Uri uri = null;
		
		if(this.state.hasStaticMap()) {
			final StaticMap map = state.getStaticMap();
			if(map.hasMediumMap()) {
				uri = getMediumStaticMapFileUri();
			} else if(map.hasSmallMap()) {
				uri = getSmallStaticMapFileUri();
			}
			
			// Large Map isn't acceptable for a notification icon.
		}
		
		return uri;
	}
	
	public String getNotificationMapFilePath() {
		String filePath = null;
		
		if(this.state.hasStaticMap()) {
			final StaticMap map = state.getStaticMap();
			if(map.hasSmallMap()) {
				filePath = map.getSmallMapFilePath();
			} else if(map.hasMediumMap()) {
				filePath = map.getMediumMapFilePath();
			}
			
			// Large Map isn't acceptable for a notification icon.
		}
		
		return filePath;
	}
	
	public Bitmap getNotificationMapImage() {
		Bitmap result = null;
		try {
			final String filePath = getNotificationMapFilePath();
			if(filePath != null) {
				result = BitmapFactory.decodeFile(filePath);
			}
		} catch(Exception e) {
			Debugging.e(e);
			result = null;
		}
		
		return result;
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
				result = getText(R.string.errordesc_try_going_outside);
			} else if(state.isError(NoLocationProvidersEnabledException.class)) {
				result = getText(R.string.errordesc_tap_here_to_turn_them_on);
			} else {
				result = getText(R.string.errordesc_tap_here_to_try_again);
			}
		}
		return result;
	}

	public CharSequence getErrorTitle() {
		CharSequence result = "";
		
		if(state.hasError()) {
			if(state.isError(CannotObtainAccurateFixException.class)) {
				result = getText(R.string.errortitle_having_trouble_locating);
			} else if(state.isError(NoLocationProvidersEnabledException.class)) {
				result = getText(R.string.errortitle_location_services_are_off);
			} else {
				result = getText(R.string.errortitle_something_went_wrong);
			}
		}
		return result;
	}

}
