package com.futonredemption.mylocation.appwidgets;

import com.futonredemption.mylocation.services.WidgetUpdateService;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class AppWidgetProvider4x1 extends AppWidgetProvider {

	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		Intent intent = new Intent(context, WidgetUpdateService.class);
		intent.putExtra("method", "FullUpdate");
		context.startService(intent);
	}
}
