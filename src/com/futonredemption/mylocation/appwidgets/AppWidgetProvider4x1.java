package com.futonredemption.mylocation.appwidgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class AppWidgetProvider4x1 extends AppWidgetProvider {

	@Override
	public void onEnabled(Context context) {
		
	}
	
	@Override
	public void onDisabled(Context context) {
		purgeApplicationData(context);
	}

	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	private void purgeApplicationData(Context context) {
		// TODO Auto-generated method stub
		
	}
}
