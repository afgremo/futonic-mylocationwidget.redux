package com.futonredemption.mylocation.tasks;

import java.util.concurrent.Future;

import android.content.Context;

import com.futonredemption.mylocation.Debugging;
import com.futonredemption.mylocation.MyLocationRetrievalState;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpRequest;
import com.google.api.client.http.json.JsonHttpRequestInitializer;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.urlshortener.Urlshortener;
import com.google.api.services.urlshortener.Urlshortener.Url.Insert;
import com.google.api.services.urlshortener.UrlshortenerRequest;
import com.google.api.services.urlshortener.model.Url;

public class ShortenUrlTask extends AbstractMyLocationTask {
	public ShortenUrlTask(Context context, MyLocationRetrievalState state) {
		super(context, state);
	}
	
	public ShortenUrlTask(Context context, Future<MyLocationRetrievalState> state) {
		super(context, state);
	}

	@Override
	protected void loadData(MyLocationRetrievalState state) {
		try {
			Debugging.breakpoint();
			Urlshortener.Builder builder;
			HttpTransport httpTransport = new NetHttpTransport();
			JsonFactory jsonFactory = new GsonFactory();
			builder = Urlshortener.builder(httpTransport, jsonFactory);
			builder.setApplicationName("My Location");
			builder.setJsonHttpRequestInitializer(new UrlshortenerRequestInitializer());
			Urlshortener shortener = builder.build();
			Url url = new Url();
			url.setLongUrl("http://javadoc.google-api-java-client.googlecode.com/hg/apis/urlshortener/v1/index.html");
			
			Insert ins = shortener.url().insert(url);
			Url newUrl = ins.execute();
			String shortUrl = newUrl.getId();
			Debugging.w(shortUrl);
		} catch(Exception e) {
			Debugging.e(e);
		}
	}
	
	private static final String APIKEY = "AIzaSyDKWzASbbywLp8IqesyaOdPRLkSyiQfWGQ";
	
	private static class UrlshortenerRequestInitializer implements JsonHttpRequestInitializer {
	      public void initialize(JsonHttpRequest request) {
	        UrlshortenerRequest urlshortenerRequest = (UrlshortenerRequest)request;
	        urlshortenerRequest.setPrettyPrint(true);
	        urlshortenerRequest.setKey(APIKEY);
	    }
	  }
}
