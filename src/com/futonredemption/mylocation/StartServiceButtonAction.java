package com.futonredemption.mylocation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartServiceButtonAction implements OnClickListener {

	private final Intent intent;
	public StartServiceButtonAction(Intent intent) {
		this.intent = intent;
	}
	public void onClick(View v) {
		final Context context = v.getContext();
		context.startService(intent);
	}

}
