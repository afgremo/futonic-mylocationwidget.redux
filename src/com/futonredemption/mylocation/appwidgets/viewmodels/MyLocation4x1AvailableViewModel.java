package com.futonredemption.mylocation.appwidgets.viewmodels;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

public class MyLocation4x1AvailableViewModel extends TwoLineMyLocationAppWidgetViewModel {

	public PendingIntent RefreshAction;
	public PendingIntent LocationDetailsAction;
	public PendingIntent ShareLocationAction;

	@Override
	protected int getLayoutId() {
		return R.layout.appwidget_4x1_default;
	}

	@Override
	protected void onCreateViews(Context context, RemoteViews views) {
	}

	@Override
	protected void onFromAdapter(DataToViewModelAdapter adapter) {
		// TODO Auto-generated method stub
		
	}
}