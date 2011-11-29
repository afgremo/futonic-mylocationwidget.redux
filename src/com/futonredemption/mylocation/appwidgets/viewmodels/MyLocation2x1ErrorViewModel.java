package com.futonredemption.mylocation.appwidgets.viewmodels;

import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

public class MyLocation2x1ErrorViewModel extends AbstractMyLocationWidgetViewModel {

	public CharSequence Title;
	public CharSequence Description;
	
	public PendingIntent RefreshAction;
	public PendingIntent FixProblemAction;
	
	@Override
	protected int getLayoutId() {
		return R.layout.aw_mylocation_2x1_error;
	}

	@Override
	protected void onCreateViews(Context context, RemoteViews views) {
		setText(views, R.id.TitleTextView, Title);
		setText(views, R.id.DescriptionTextView, Description);
		setOnClick(views, R.id.ActionImageButton, RefreshAction);
		setOnClick(views, R.id.DetailButtonLinearLayout, FixProblemAction);
	}

	@Override
	protected void onFromAdapter(DataToViewModelAdapter adapter) {
		Title = adapter.getErrorTitle();
		Description = adapter.getErrorDescription();
		RefreshAction = adapter.getPendingRefreshAction();
		FixProblemAction = adapter.getPendingOpenLocationSettingsAction();
	}
}
