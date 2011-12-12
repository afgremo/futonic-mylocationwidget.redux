package com.futonredemption.mylocation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartActivityButtonAction implements OnClickListener {

	private final Intent intent;
	public StartActivityButtonAction(Intent intent) {
		this.intent = intent;
	}
	public void onClick(View v) {
		final Context context = v.getContext();
		context.startActivity(intent);
	}
}
