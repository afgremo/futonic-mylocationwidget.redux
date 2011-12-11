package com.futonredemption.mylocation.notifications;

import com.futonredemption.mylocation.DataToViewModelAdapter;

public interface INotification {

	public void fromAdapter(DataToViewModelAdapter adapter);
	public void publish();
}
