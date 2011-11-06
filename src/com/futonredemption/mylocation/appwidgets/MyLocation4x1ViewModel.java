package com.futonredemption.mylocation.appwidgets;

import com.futonredemption.mylocation.BundleToViewModelAdapter;
import com.futonredemption.mylocation.R;

import android.content.Context;
import android.widget.RemoteViews;

public class MyLocation4x1ViewModel implements MyLocationViewModel {

	public CharSequence Title;
	public CharSequence Description;
	
	public RemoteViews createViews(Context context) {
		final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_4x1_loading);
		
		views.setTextViewText(R.id.TitleTextView, Title);
		views.setTextViewText(R.id.DescriptionTextView, Description);
		
		return views;
	}

	public void fromAdapter(BundleToViewModelAdapter adapter) {
		Title = adapter.getTitle();
		Description = adapter.getDescription();
	}
}
