package com.futonredemption.mylocation.appwidgets.viewmodels;

import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

public class MyLocation2x1LoadingViewModel extends AbstractMyLocationWidgetViewModel {

	PendingIntent LocationSettingsAction;
	MultiTextFieldWriter writer = new MultiTextFieldWriter();
	
	protected int getLayoutId() {
		return R.layout.aw_mylocation_2x1_loading;
	}

	public void onCreateViews(Context context, RemoteViews views) {
		writer.bindViews(views);
		
		setOnClick(views, R.id.AppWidgetBackground, LocationSettingsAction);
	}

	public void onFromAdapter(DataToViewModelAdapter adapter) {
		LocationSettingsAction = adapter.getPendingOpenLocationSettingsAction();
	}
}
