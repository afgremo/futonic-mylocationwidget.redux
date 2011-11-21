package com.futonredemption.mylocation.appwidgets;

import com.futonredemption.mylocation.appwidgets.viewmodels.IMyLocationAppWidgetViewModel;
import com.futonredemption.mylocation.appwidgets.viewmodels.IMyLocationStateViewModelSelectables;
import com.futonredemption.mylocation.appwidgets.viewmodels.MyLocation1x1AvailableViewModel;
import com.futonredemption.mylocation.appwidgets.viewmodels.MyLocation1x1ErrorViewModel;
import com.futonredemption.mylocation.appwidgets.viewmodels.MyLocation1x1LoadingViewModel;
import com.futonredemption.mylocation.services.WidgetUpdateService;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class AppWidgetProvider1x1 extends AppWidgetProvider {

	@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		WidgetUpdateService.beginSyncLatestKnownLocation(context);
	}

	// TODO: Fix this for 1x1
	public static IMyLocationStateViewModelSelectables ViewModelSelectables = new IMyLocationStateViewModelSelectables() {

		public IMyLocationAppWidgetViewModel getAvailable() {
			return new MyLocation1x1AvailableViewModel();
		}

		public IMyLocationAppWidgetViewModel getError() {
			return new MyLocation1x1ErrorViewModel();
		}

		public IMyLocationAppWidgetViewModel getLoading() {
			return new MyLocation1x1LoadingViewModel();
		}
	};
}
