package com.futonredemption.mylocation.appwidgets.viewmodels;

import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

public class MyLocation4x1LoadingViewModel implements IMyLocationAppWidgetViewModel {

	protected int getLayoutId() {
		return R.layout.appwidget_4x1_loading;
	}

	public RemoteViews createViews(Context context) {
		final RemoteViews views = new RemoteViews(context.getPackageName(), getLayoutId());
		return views;
	}

	public void fromAdapter(DataToViewModelAdapter adapter) {
		// TODO Auto-generated method stub
		
	}
}
