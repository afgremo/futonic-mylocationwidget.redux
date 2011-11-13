package com.futonredemption.mylocation.appwidgets.viewmodels;

public interface IMyLocationStateViewModelSelectables {
	IMyLocationAppWidgetViewModel getAvailable();
	IMyLocationAppWidgetViewModel getError();
	IMyLocationAppWidgetViewModel getLoading();
}
