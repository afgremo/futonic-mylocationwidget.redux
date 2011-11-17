package com.futonredemption.mylocation.appwidgets.viewmodels;

import org.beryl.diagnostics.Logger;

import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

public abstract class TwoLineMyLocationAppWidgetViewModel implements IMyLocationAppWidgetViewModel {
	
	public CharSequence Title;
	public CharSequence Description;
	
	public final RemoteViews createViews(Context context) {
		final RemoteViews views = new RemoteViews(context.getPackageName(), getLayoutId());
		
		Logger.w(Title);
		views.setTextViewText(R.id.TitleTextView, Title);
		views.setTextViewText(R.id.DescriptionTextView, Description);
		
		onCreateViews(context, views);
		return views;
	}
	
	public void fromAdapter(final DataToViewModelAdapter adapter) {
		Title = adapter.getTitle();
		Description = adapter.getDescription();
		onFromAdapter(adapter);
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
