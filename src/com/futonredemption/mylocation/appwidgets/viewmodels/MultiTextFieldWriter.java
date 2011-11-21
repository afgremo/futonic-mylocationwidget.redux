package com.futonredemption.mylocation.appwidgets.viewmodels;

import java.util.ArrayList;
import java.util.List;

import android.widget.RemoteViews;

public class MultiTextFieldWriter {

	final List<CharSequence> messages = new ArrayList<CharSequence>();
	final List<Integer> textViews = new ArrayList<Integer>();
	
	public void addView(int resId) {
		textViews.add(resId);
	}
	
	public void addText(CharSequence message) {
		messages.add(message);
	}
	
	public CharSequence getText(int position) {
		return messages.get(position % messages.size());
	}
	
	public void bindViews(final RemoteViews views) {
		int i;
		final int viewSize = textViews.size();
		
		for(i = 0; i < viewSize; i++) {
			views.setTextViewText(textViews.get(i), getText(i));
		}
	}
}
