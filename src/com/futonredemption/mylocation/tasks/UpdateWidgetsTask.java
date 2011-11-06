package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import org.beryl.appwidget.AppWidgetViewModel;
import org.beryl.diagnostics.Logger;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.appwidgets.AppWidgetProvider4x1;
import com.futonredemption.mylocation.appwidgets.MyLocation4x1ViewModel;

public class UpdateWidgetsTask extends AbstractMyLocationTask {

	public UpdateWidgetsTask(Context context, Future<MyLocationBundle> bundle) {
		super(context, bundle);
	}
	
	@Override
	protected void appendLocationData(MyLocationBundle bundle) {
		Logger.i("Update Widgets");
		final AppWidgetManager manager = AppWidgetManager.getInstance(context);
		final MyLocation4x1ViewModel vm4x1 = new MyLocation4x1ViewModel();
		vm4x1.Title = String.format("Lat: %s", bundle.getLocation().getLatitude());
		vm4x1.Description = String.format("Long: %s", bundle.getLocation().getLongitude());
		
		updateWidgets(manager, AppWidgetProvider4x1.class, vm4x1);
	}

	private void updateWidgets(AppWidgetManager manager, Class<?> clazz, AppWidgetViewModel vm) {
		final int[] ids = manager.getAppWidgetIds(new ComponentName(context, clazz));
		final RemoteViews views = vm.createViews(context);
		manager.updateAppWidget(ids, views);
	}
}
