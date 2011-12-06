package com.futonredemption.mylocation.appwidgets.viewmodels;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.widget.RemoteViews;

public class MyLocation1x1AvailableViewModel extends AbstractMyLocationWidgetViewModel {

	public PendingIntent LocationDetailsAction;
	Uri fileUri;
	
	@Override
	protected int getLayoutId() {
		return R.layout.aw_mylocation_1x1_available;
	}

	@Override
	protected void onCreateViews(Context context, RemoteViews views) {
		if(fileUri != null) {
			views.setUri(R.id.ActionImageButton, "setImageURI", fileUri);
			setOnClick(views, R.id.ActionImageButton, LocationDetailsAction);
		}
	}

	@Override
	protected void onFromAdapter(DataToViewModelAdapter adapter) {
		fileUri = adapter.getStaticMapFileUri();
		LocationDetailsAction = adapter.getPendingOpenLocationCardAction();
	}
}
