package com.futonredemption.mylocation.notifications;

import android.content.Context;

import com.futonredemption.mylocation.DataToViewModelAdapter;

public class HoneycombOrHigherNotification extends GingerbreadAndLowerNotification {

	public HoneycombOrHigherNotification(Context context) {
		super(context);
	}

	@Override
	public void fromAdapter(final DataToViewModelAdapter adapter) {
		super.fromAdapter(adapter);
	}

	@Override
	public void publish() {
		super.publish();
	}
}
