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
	
	private void setupWriter() {
		writer.addView(R.id.Description1TextView);
		writer.addView(R.id.Description2TextView);
		writer.addView(R.id.Description3TextView);
		writer.addView(R.id.Description4TextView);
	}

	public void onFromAdapter(DataToViewModelAdapter adapter) {
		boolean gpsDisabled = adapter.isGpsLocationDisabled();
		boolean networkDisabled = adapter.isNetworkLocationDisabled();
		boolean gpsEnabled = adapter.isGpsLocationEnabled();
		
		setupWriter();
		
		if(gpsDisabled) {
			writer.addText(adapter.getText(R.string.loadingtext_gps_is_off));
		}
		
		writer.addText(adapter.getText(R.string.loadingtext_pinpointing_location));
		
		if(networkDisabled) {
			writer.addText(adapter.getText(R.string.loadingtext_network_is_off));
		}
		
		if(gpsEnabled) {
			writer.addText(adapter.getText(R.string.loadingtext_go_outside));
		}
		
		LocationSettingsAction = adapter.getPendingOpenLocationSettingsAction();
	}
}
