package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import android.content.Context;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.futonredemption.mylocation.google.urlshortener.GooglShortener;

public class ShortenUrlTask extends AbstractMyLocationTask {
	public ShortenUrlTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public ShortenUrlTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		String longUrl = state.getLongMapsUrl();
		
		String shortUrl = tryShortenUrl(longUrl, 3);
		
		state.setShortMapsUrl(shortUrl);
	}
	
	protected String tryShortenUrl(final String longUrl, final int attempts) {
		GooglShortener shortener = new GooglShortener();
		
		String shortUrl = null;
		for(int i = 0; i < attempts && shortUrl == null; i++) {
			try {
				shortUrl = shortener.shortenUrl(longUrl);
			} catch(Exception e) {
				Debugging.e(e);
			}
		}
		return shortUrl;
	}
}
