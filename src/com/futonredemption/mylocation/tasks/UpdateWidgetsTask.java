package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import org.beryl.diagnostics.Logger;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.appwidgets.AppWidgetProvider4x1;
import com.futonredemption.mylocation.appwidgets.viewmodels.IMyLocationAppWidgetViewModel;
import com.futonredemption.mylocation.appwidgets.viewmodels.IMyLocationStateViewModelSelectables;
import com.futonredemption.mylocation.appwidgets.viewmodels.ViewModelStateSelector;

public class UpdateWidgetsTask extends AbstractMyLocationTask {

	public UpdateWidgetsTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public UpdateWidgetsTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		Logger.i("Update Widgets");
		final DataToViewModelAdapter adapter = new DataToViewModelAdapter(context, state);
		updateAllWidgets(adapter);
	}
	
	@Override
	protected void loadDataFromErrorState(MyLocationRetrievalState state) {
		loadData(state);
	}
	
	private void updateAllWidgets(DataToViewModelAdapter adapter) {
		final AppWidgetManager manager = AppWidgetManager.getInstance(context);
		updateWidgets(manager, AppWidgetProvider4x1.class, AppWidgetProvider4x1.ViewModelSelectables, adapter);
	}

	private void updateWidgets(AppWidgetManager manager, Class<?> clazz,
			IMyLocationStateViewModelSelectables selectables, DataToViewModelAdapter adapter) {
		ViewModelStateSelector selector = new ViewModelStateSelector();
		IMyLocationAppWidgetViewModel vm = selector.getViewModel(adapter, selectables);
		final int[] ids = manager.getAppWidgetIds(new ComponentName(context, clazz));
		vm.fromAdapter(adapter);
		final RemoteViews views = vm.createViews(context);
		manager.updateAppWidget(ids, views);
	}
}
