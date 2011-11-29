package com.futonredemption.mylocation.appwidgets.viewmodels;

import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;

public abstract class AbstractMyLocationWidgetViewModel implements IMyLocationAppWidgetViewModel {

	public final RemoteViews createViews(Context context) {
		final RemoteViews views = new RemoteViews(context.getPackageName(), getLayoutId());
		onCreateViews(context, views);
		return views;
	}

	public final void fromAdapter(DataToViewModelAdapter adapter) {
		onFromAdapter(adapter);
	}

	protected void setText(RemoteViews views, int viewId, CharSequence text) {
		views.setTextViewText(viewId, text);
	}
	
	protected void setOnClick(RemoteViews views, int viewId, PendingIntent action) {
		if(action != null) {
			views.setOnClickPendingIntent(viewId, action);
		}
	}
	
	protected abstract void onFromAdapter(final DataToViewModelAdapter adapter);
	protected abstract int getLayoutId();
	protected abstract void onCreateViews(Context context, RemoteViews views);
}
