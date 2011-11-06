package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import org.beryl.diagnostics.Logger;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.BundleToViewModelAdapter;
import com.futonredemption.mylocation.MyLocationBundle;
import com.futonredemption.mylocation.appwidgets.AppWidgetProvider4x1;
import com.futonredemption.mylocation.appwidgets.MyLocation4x1ViewModel;
import com.futonredemption.mylocation.appwidgets.MyLocationViewModel;

public class UpdateWidgetsTask extends AbstractMyLocationTask {

	public UpdateWidgetsTask(Context context, Future<MyLocationBundle> bundle) {
		super(context, bundle);
	}
	
	@Override
	protected void appendLocationData(MyLocationBundle bundle) {
		Logger.i("Update Widgets");
		final BundleToViewModelAdapter adapter = new BundleToViewModelAdapter(context, bundle);
		updateAllWidgets(adapter);
	}
	@Override
	protected void onEmptyLocation() {
		final BundleToViewModelAdapter adapter = new BundleToViewModelAdapter(context, new MyLocationBundle(null));
		updateAllWidgets(adapter);
	}

	private void updateAllWidgets(BundleToViewModelAdapter adapter) {
		final AppWidgetManager manager = AppWidgetManager.getInstance(context);
		final MyLocation4x1ViewModel vm4x1 = new MyLocation4x1ViewModel();
		updateWidgets(manager, AppWidgetProvider4x1.class, vm4x1, adapter);
	}

	private void updateWidgets(AppWidgetManager manager, Class<?> clazz, MyLocationViewModel vm, BundleToViewModelAdapter adapter) {
		final int[] ids = manager.getAppWidgetIds(new ComponentName(context, clazz));
		vm.fromAdapter(adapter);
		final RemoteViews views = vm.createViews(context);
		manager.updateAppWidget(ids, views);
	}
}
