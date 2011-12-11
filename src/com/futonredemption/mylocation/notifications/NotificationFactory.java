package com.futonredemption.mylocation.notifications;

import org.beryl.app.AndroidVersion;

import android.content.Context;

public class NotificationFactory {

	public static INotification createNotification(final Context context) {
		if(AndroidVersion.isHoneycombOrHigher()) {
			return new HoneycombOrHigherNotification(context);
		} else {
			return new GingerbreadAndLowerNotification(context);
		}
	}
}
