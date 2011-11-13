package com.futonredemption.mylocation.appwidgets.viewmodels;

import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

public class MyLocation4x1ErrorViewModel extends TwoLineMyLocationAppWidgetViewModel {

	public PendingIntent RefreshAction;
	public PendingIntent FixProblemAction;
	
	@Override
	protected int getLayoutId() {
		return R.layout.appwidget_4x1_error;
	}

	@Override
	protected void onCreateViews(Context context, RemoteViews views) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFromAdapter(DataToViewModelAdapter adapter) {
		// TODO Auto-generated method stub
		
	}

}
