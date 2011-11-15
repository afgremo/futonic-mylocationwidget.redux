package com.futonredemption.mylocation.appwidgets.viewmodels;

import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

public class MyLocation4x1LoadingViewModel extends TwoLineMyLocationAppWidgetViewModel {

	@Override
	protected int getLayoutId() {
		return R.layout.appwidget_4x1_loading;
	}

	@Override
	protected void onCreateViews(Context context, RemoteViews views) {
	}

	@Override
	protected void onFromAdapter(DataToViewModelAdapter adapter) {
	}
}
