package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.appwidgets.AppWidgetProvider1x1;
import com.futonredemption.mylocation.appwidgets.AppWidgetProvider2x1;
import com.futonredemption.mylocation.appwidgets.AppWidgetProvider4x1;
import com.futonredemption.mylocation.appwidgets.viewmodels.IMyLocationAppWidgetViewModel;
import com.futonredemption.mylocation.appwidgets.viewmodels.IMyLocationStateViewModelSelectables;
import com.futonredemption.mylocation.appwidgets.viewmodels.ViewModelStateSelector;
import com.futonredemption.mylocation.notifications.INotification;
import com.futonredemption.mylocation.notifications.NotificationFactory;

public class UpdateWidgetsTask extends AbstractMyLocationTask {

	public UpdateWidgetsTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public UpdateWidgetsTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		Debugging.w("UpdateWidgetsTask, updating...");
		final DataToViewModelAdapter adapter = new DataToViewModelAdapter(context, state);
		updateAllWidgets(adapter);
	}
	
	@Override
	protected void loadDataFromErrorState(MyLocationRetrievalState state) {
		loadData(state);
	}
	
	private void updateAllWidgets(DataToViewModelAdapter adapter) {
		final AppWidgetManager manager = AppWidgetManager.getInstance(context);
		
		// Notifications
		updateNotifications(adapter);
		
		// Legacy Widgets
		updateWidgets(manager, AppWidgetProvider1x1.class, AppWidgetProvider1x1.ViewModelSelectables, adapter);
		updateWidgets(manager, AppWidgetProvider2x1.class, AppWidgetProvider2x1.ViewModelSelectables, adapter);
		updateWidgets(manager, AppWidgetProvider4x1.class, AppWidgetProvider4x1.ViewModelSelectables, adapter);
	}

	private void updateNotifications(final DataToViewModelAdapter adapter) {
		INotification notifier = NotificationFactory.createNotification(context);
		notifier.fromAdapter(adapter);
		notifier.publish();
	}

	private void updateWidgets(final AppWidgetManager manager, final Class<?> clazz,
			final IMyLocationStateViewModelSelectables selectables, final DataToViewModelAdapter adapter) {
		ViewModelStateSelector selector = new ViewModelStateSelector();
		IMyLocationAppWidgetViewModel vm = selector.getViewModel(adapter, selectables);
		final int[] ids = manager.getAppWidgetIds(new ComponentName(context, clazz));
		vm.fromAdapter(adapter);
		final RemoteViews views = vm.createViews(context);
		manager.updateAppWidget(ids, views);
	}
}
