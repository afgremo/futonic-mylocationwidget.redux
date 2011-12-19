package com.futonredemption.mylocation.appwidgets.viewmodels;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

public class MyLocation4x1AvailableViewModel extends AbstractMyLocationWidgetViewModel {

	public CharSequence Title;
	public CharSequence Description;
	
	public PendingIntent RefreshAction;
	public PendingIntent LocationDetailsAction;
	public PendingIntent ShareLocationAction;

	@Override
	protected int getLayoutId() {
		return R.layout.aw_mylocation_4x1_available;
	}

	@Override
	protected void onCreateViews(Context context, RemoteViews views) {
		setText(views, R.id.TitleTextView, Title);
		setText(views, R.id.DescriptionTextView, Description);
		
		setOnClick(views, R.id.ActionImageButton, RefreshAction);
		setOnClick(views, R.id.ShareImageButton, ShareLocationAction);
		setOnClick(views, R.id.DetailButtonLinearLayout, LocationDetailsAction);
	}

	@Override
	protected void onFromAdapter(DataToViewModelAdapter adapter) {
		Title = adapter.getTitle();
		Description = adapter.getDescription();
		
		RefreshAction = adapter.getPendingRefreshAction();
		LocationDetailsAction = adapter.getPendingOpenLocationCardAction();
		ShareLocationAction = adapter.getPendingShareLocationAction();
	}

}
