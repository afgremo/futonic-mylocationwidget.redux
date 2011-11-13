package com.futonredemption.mylocation.appwidgets.viewmodels;

import com.futonredemption.mylocation.DataToViewModelAdapter;

public class ViewModelStateSelector {

	public ViewModelStateSelector() {
		
	}
	
	public IMyLocationAppWidgetViewModel getViewModel(
			DataToViewModelAdapter adapter,
			IMyLocationStateViewModelSelectables selectables) {
		if(adapter.isAvailable()) {
			return selectables.getAvailable();
		} else if(adapter.isLoading()) {
			return selectables.getLoading();
		} else {
			return selectables.getError();
		}
	}
}

	
