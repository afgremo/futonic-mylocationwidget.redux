package com.futonredemption.mylocation.appwidgets;

import org.beryl.appwidget.AppWidgetViewModel;

import com.futonredemption.mylocation.BundleToViewModelAdapter;

public interface MyLocationViewModel extends AppWidgetViewModel {
	void fromAdapter(BundleToViewModelAdapter adapter);
}
