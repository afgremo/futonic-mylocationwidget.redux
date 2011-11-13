package com.futonredemption.mylocation.appwidgets.viewmodels;

import org.beryl.appwidget.AppWidgetViewModel;

import com.futonredemption.mylocation.DataToViewModelAdapter;

public interface IMyLocationAppWidgetViewModel extends AppWidgetViewModel {
	void fromAdapter(DataToViewModelAdapter adapter);
}
