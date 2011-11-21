package com.futonredemption.mylocation.appwidgets.viewmodels;

import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

public class MyLocation1x1ErrorViewModel extends AbstractMyLocationWidgetViewModel {

	public PendingIntent RefreshAction;
	public PendingIntent FixProblemAction;
	
	@Override
	protected int getLayoutId() {
		return R.layout.aw_mylocation_1x1_error;
	}

	@Override
	protected void onCreateViews(Context context, RemoteViews views) {
		views.setOnClickPendingIntent(R.id.ActionImageButton, RefreshAction);
	}

	@Override
	protected void onFromAdapter(DataToViewModelAdapter adapter) {
		RefreshAction = adapter.getPendingRefreshAction();
	}
}
