package com.futonredemption.mylocation.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;
import com.futonredemption.mylocation.appwidgets.AppWidgetProvider1x1;
import com.futonredemption.mylocation.appwidgets.AppWidgetProvider2x1;
import com.futonredemption.mylocation.appwidgets.AppWidgetProvider4x1;

public class GingerbreadAndLowerNotification implements INotification {

	private static final int CustomMessageId = 1;
	protected Notification notify = null;
	public static final int INTERVAL_NotificationLed = 5000;
	
	protected final Context context;
	protected final AppWidgetManager manager;
	
	public GingerbreadAndLowerNotification(final Context context) {
		this.context = context;
		this.manager = AppWidgetManager.getInstance(context);
	}
	
	public void fromAdapter(final DataToViewModelAdapter adapter) {
		
		if(shouldPublishNotification()) {
			if(adapter.hasJustStarted()) {
				notifyIsLoading(adapter);
			} else {
				notifyAddress(adapter);
			}
		}
	}

	private void notifyAddress(final DataToViewModelAdapter adapter) {
		notify = createBaseNotification();
		CharSequence contentText;
		PendingIntent contentIntent;
		CharSequence contentTitle;
		if(adapter.isAvailable() || adapter.isLoading()) {
			contentTitle = adapter.getTitle();
			contentText = adapter.getDescription();
			contentIntent = adapter.getPendingMainActivityAction();
		} else {
			contentTitle = adapter.getErrorTitle();
			contentText = adapter.getErrorDescription();
			contentIntent = adapter.getPendingOpenLocationSettingsAction();
		}
		
		notify.flags = Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
		notify.icon = R.drawable.ic_stat_findlocation;
		notify.ledOffMS = INTERVAL_NotificationLed;
		notify.ledOnMS = INTERVAL_NotificationLed;
		notify.ledARGB = 0xff0000ff;
		notify.tickerText = contentTitle + " " + contentText;
		notify.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	}
	
	private void notifyIsLoading(final DataToViewModelAdapter adapter) {
		notify = createBaseNotification();
		notify.tickerText = getText(R.string.finding_location);
		notify.flags = Notification.FLAG_ONGOING_EVENT;
		CharSequence contentText;
		PendingIntent contentIntent;
		
		if(adapter.isGpsLocationDisabled()) {
			contentText = getText(R.string.loadingtext_gps_is_off);
			contentIntent = adapter.getPendingOpenLocationSettingsAction();
		} else if(adapter.isNetworkLocationDisabled()) {
			contentText = getText(R.string.loadingtext_network_is_off);
			contentIntent = adapter.getPendingOpenLocationSettingsAction();
		} else {
			contentText = getText(R.string.loadingtext_go_outside);
			contentIntent = adapter.getPendingOpenLocationSettingsAction();
		}
		
		notify.setLatestEventInfo(context, getText(R.string.pinpointing_location), contentText, contentIntent);
	}
	
	private Notification createBaseNotification() {
		final Notification notifier = new Notification();
		notifier.icon = R.drawable.ic_stat_findlocation;
		return notifier;
	}
	
	public void publish() {
		final NotificationManager nm = getNotificationManager();
		if(notify != null) {
			nm.notify(CustomMessageId, notify);
		} else {
			nm.cancel(CustomMessageId);
		}
	}
	
	protected NotificationManager getNotificationManager() {
		return (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	
	protected boolean shouldPublishNotification() {
		return ! hasWidgetThatPreventsNotification() &&
				hasWidgetThatNeedsNotification();
	}
	protected boolean hasWidgetThatPreventsNotification() {
		boolean result = false;
		
		result = (result) ? true : isWidgetOnScreen(AppWidgetProvider4x1.class);
		
		return result;
	}
	
	protected boolean hasWidgetThatNeedsNotification() {
		boolean result = false;
		
		result = (result) ? true : isWidgetOnScreen(AppWidgetProvider1x1.class);
		result = (result) ? true : isWidgetOnScreen(AppWidgetProvider2x1.class);
		
		return result;
	}
	
	protected boolean isWidgetOnScreen(final Class<?> clazz) {
		final int [] ids = manager.getAppWidgetIds(new ComponentName(context, clazz));
		return ids.length > 0;
	}
	
	protected CharSequence getText(int resId) {
		return context.getText(resId);
	}
}
