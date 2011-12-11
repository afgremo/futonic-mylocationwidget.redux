package com.futonredemption.mylocation.notifications;

import android.app.Notification;
import android.app.Notification.Builder;
import android.content.Context;

import com.futonredemption.mylocation.DataToViewModelAdapter;

public class HoneycombOrHigherNotification extends GingerbreadAndLowerNotification {

	public HoneycombOrHigherNotification(Context context) {
		super(context);
	}
	
	@Override
	public void fromAdapter(final DataToViewModelAdapter adapter) {
		final Notification.Builder builder = createBuilder();
		if(adapter.hasBasicInformation()) {
			buildLocationAvailableNotification(adapter, builder);
		} else if(adapter.isLoading()) {
			buildIsLoadingNotification(adapter, builder);
		} else {
			buildErrorNotification(adapter, builder);
		}
		this.notify = builder.getNotification();
	}

	private void buildLocationAvailableNotification(DataToViewModelAdapter adapter,
			Builder builder) {
		builder.setContentTitle(adapter.getTitle());
		builder.setContentText(adapter.getDescription());
		builder.setTicker(adapter.getTickerText());
		builder.setLights(NotificationLightColor, NotificationLightInterval, NotificationLightInterval);
		builder.setContentInfo("setContentInfo"); // TODO: Just to figure out what this is.
		builder.setLargeIcon(adapter.getNotificationMapImage());
		builder.setOngoing(false);
	}
	
	private void buildErrorNotification(DataToViewModelAdapter adapter,
			Builder builder) {
		builder.setContentTitle(getErrorTitle(adapter));
		builder.setContentText(getErrorText(adapter));
		builder.setTicker(getErrorTickerText(adapter));
		builder.setOngoing(false);
	}

	private void buildIsLoadingNotification(DataToViewModelAdapter adapter,
			Builder builder) {
		builder.setContentTitle(getLoadingTitle());
		builder.setContentText(getLoadingText(adapter));
		builder.setTicker(getLoadingTickerText());
		builder.setAutoCancel(false);
		builder.setOngoing(true);
	}

	protected Notification.Builder createBuilder() {
		final Notification.Builder builder = new Notification.Builder(context);
		builder.setOnlyAlertOnce(true);
		builder.setAutoCancel(true);
		builder.setSmallIcon(getNotificationIcon());
		return builder;
	}
}
