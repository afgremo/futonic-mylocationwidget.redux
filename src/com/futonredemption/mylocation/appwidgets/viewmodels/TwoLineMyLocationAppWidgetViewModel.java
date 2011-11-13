package com.futonredemption.mylocation.appwidgets.viewmodels;

import android.content.Context;
import android.widget.RemoteViews;

import com.futonredemption.mylocation.DataToViewModelAdapter;
import com.futonredemption.mylocation.R;

public abstract class TwoLineMyLocationAppWidgetViewModel implements IMyLocationAppWidgetViewModel {
	
	public CharSequence Title;
	public CharSequence Description;
	
	public final RemoteViews createViews(Context context) {
		final RemoteViews views = new RemoteViews(context.getPackageName(), getLayoutId());
		
		views.setTextViewText(R.id.TitleTextView, Title);
		views.setTextViewText(R.id.DescriptionTextView, Description);
		
		onCreateViews(context, views);
		return null;
	}
	
	public void fromAdapter(final DataToViewModelAdapter adapter) {
		Title = adapter.getTitle();
		Description = adapter.getDescription();
		onFromAdapter(adapter);
	}
	
	protected abstract void onFromAdapter(final DataToViewModelAdapter adapter);
	protected abstract int getLayoutId();
	protected abstract void onCreateViews(Context context, RemoteViews views);
}