package com.futonredemption.mylocation.appwidgets;

import com.futonredemption.mylocation.R;
import com.futonredemption.mylocation.services.WidgetUpdateService;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class DebugForceRefreshProvider extends AppWidgetProvider {

	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.debug_aw_forcerefresh);
		Intent fullUpdate = WidgetUpdateService.getBeginFullUpdate(context);
		PendingIntent debugFullUpdatePendingIntent = PendingIntent.getService(context, fullUpdate.hashCode(),
				fullUpdate, PendingIntent.FLAG_UPDATE_CURRENT);
		Intent syncLatest = WidgetUpdateService.getSyncToLatestKnownLocation(context);
		PendingIntent debugSyncLatestPendingIntent = PendingIntent.getService(context, syncLatest.hashCode(),
				syncLatest, PendingIntent.FLAG_UPDATE_CURRENT);
		
		views.setOnClickPendingIntent(R.id.DebugFullUpdateImageButton, debugFullUpdatePendingIntent);
		views.setOnClickPendingIntent(R.id.DebugSyncLatestImageButton, debugSyncLatestPendingIntent);
		
		appWidgetManager.updateAppWidget(appWidgetIds, views);
	}
}
